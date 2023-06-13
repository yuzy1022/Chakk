package com.example.chack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import android.preference.*;
import android.widget.Toast;

import androidx.preference.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {
        private Preference logout;
        private Preference iddelete;
        private FirebaseAuth mAuth;
        private String nickname;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                nickname = getArguments().getString("nickname");
            }

            // Intent를 통해 전달된 "nickname" 값을 가져옵니다.
            if (getActivity() != null) {
                Intent intent = getActivity().getIntent();
                if (intent != null) {
                    nickname = intent.getStringExtra("nickname");
                }
            }
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            logout = findPreference("logout");
            iddelete = findPreference("iddelete");
            mAuth = FirebaseAuth.getInstance();

            logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("로그아웃").setMessage("로그아웃하시겠습니까?")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 확인 버튼을 누를 경우 로그아웃 수행
                                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                        firebaseAuth.signOut();
                                        Intent intent = new Intent(getActivity(), Login.class);
                                        startActivity(intent);
                                        requireActivity().finish();
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 취소 버튼을 누를 경우 아무 동작 없이 팝업 닫기
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    } else if (nickname != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("로그아웃").setMessage("로그아웃하시겠습니까?")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 확인 버튼을 누를 경우 로그아웃 수행
                                        nickname = null;
                                        UserApiClient.getInstance().unlink(new Function1<Throwable, Unit>() {
                                            @Override
                                            public Unit invoke(Throwable throwable) {
                                                if (throwable != null)
                                                    Log.d("kakao_logout", "프로퍼티 제거 실패");
                                                else
                                                    Log.d("kakao_logout", "프로퍼티 제거 성공");
                                                return null;
                                            }
                                        });
                                        UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                                            @Override
                                            public Unit invoke(Throwable throwable) {
                                                Log.d("kakao", "로그아웃");
                                                Intent intent = new Intent(getActivity(), Login.class);
                                                startActivity(intent);
                                                requireActivity().finish();
                                                return null;
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 취소 버튼을 누를 경우 아무 동작 없이 팝업 닫기
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("알림").setMessage("로그인 후 이용해주세요.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getActivity(), Login.class);
                                        startActivity(intent);
                                        requireActivity().finish();
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }

                    return false;
                }
            });

            //계정탈퇴
            iddelete.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("계정 탈퇴").setMessage("정말로 계정 탈퇴하시겠습니까?")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Firebase 계정 탈퇴 수행
                                        currentUser.delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getActivity(), "Firebase 계정이 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                                            // Firebase 계정 삭제 후 로그인 화면으로 이동
                                                            Intent intent = new Intent(getActivity(), Login.class);
                                                            startActivity(intent);
                                                            requireActivity().finish();
                                                        } else {
                                                            Toast.makeText(getActivity(), "Firebase 계정 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 취소 버튼을 누를 경우 아무 동작 없이 팝업 닫기
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }else if(nickname!=null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("계정 탈퇴").setMessage("정말로 계정 탈퇴하시겠습니까?")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(nickname!=null){
                                            // 확인 버튼을 누를 경우 회원 탈퇴 수행
                                            nickname=null;
                                            UserApiClient.getInstance().unlink(new Function1<Throwable, Unit>() {
                                                @Override
                                                public Unit invoke(Throwable throwable) {
                                                    if (throwable != null)
                                                        Log.d("kakao_logout", "회원 탈퇴 실패");
                                                    else
                                                        Log.d("kakao_logout", "회원 탈퇴 성공");
                                                    return null;
                                                }
                                            });
                                            UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                                                @Override
                                                public Unit invoke(Throwable throwable) {
                                                    Log.d("kakao", "로그아웃");
                                                    Intent intent = new Intent(getActivity(), Login.class);
                                                    startActivity(intent);
                                                    requireActivity().finish();
                                                    return null;
                                                }
                                            });}
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 취소 버튼을 누를 경우 아무 동작 없이 팝업 닫기
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("알림").setMessage("로그인 후 이용해주세요.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getActivity(), Login.class);
                                        startActivity(intent);
                                        requireActivity().finish();
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                    return true;
                }
            });

        }




        @Override
        public boolean onPreferenceClick(final Preference preference) {
            // 클릭 이벤트 처리
            return false;
        }
    }
}
