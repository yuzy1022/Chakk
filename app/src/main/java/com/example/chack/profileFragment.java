package com.example.chack;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.chack.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class profileFragment extends Fragment {

    private TextView profileIdTextView;
    private String nickname;
    private FirebaseAuth mAuth;
    public profileFragment() {
        // Required empty public constructor
    }

    public static profileFragment newInstance(String nickname) {
        profileFragment fragment = new profileFragment();
        Bundle args = new Bundle();
        args.putString("nickname", nickname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nickname = getArguments().getString("nickname");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        profileIdTextView = view.findViewById(R.id.profileId);
        if (nickname != null) {
            String profileId = "ID: " + nickname;
            profileIdTextView.setText(profileId);
        }else{profileIdTextView.setText(null);}

        TextView logoutButton = view.findViewById(R.id.logOut);
        TextView accountRemove = view.findViewById(R.id.accountRemove);
        TextView announcement = view.findViewById(R.id.announcement);
        TextView appInfo = view.findViewById(R.id.appInfo);
        announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 팝업 창을 띄웁니다.
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("공지사항").setMessage("이 애플리케이션은 아직 개발중에 있습니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 확인 버튼을 누를 경우 아무 동작 없이 팝업 닫기
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });

        appInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 팝업 창을 띄웁니다.
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("앱 정보").setMessage("버전 1.0.0")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 확인 버튼을 누를 경우 아무 동작 없이 팝업 닫기
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null){
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
                }else if(nickname!=null){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("로그아웃").setMessage("로그아웃하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 확인 버튼을 누를 경우 로그아웃 수행
                                nickname=null;
                                profileIdTextView.setText(nickname);
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
                }}
        });


        accountRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                    profileIdTextView.setText(nickname);
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
                }}
        });

        return view;
    }

}