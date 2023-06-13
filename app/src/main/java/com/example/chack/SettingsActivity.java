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
import androidx.preference.*;

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
        private Preference feedback;
        private Preference logout;
        private Preference signout;
        private Preference opensource;
        private Preference image;
        private Preference nickname;


        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            logout = findPreference("logout");

           /* feedback = findPreference("feedback");
            signout = findPreference("signout");
            opensource = findPreference("opensource");
            image = findPreference("image");
            nickname = findPreference("nickname");

            feedback.setOnPreferenceClickListener(this);  */

           /* signout.setOnPreferenceClickListener(this);
            opensource.setOnPreferenceClickListener(this);
            image.setOnPreferenceClickListener(this);
            nickname.setOnPreferenceClickListener(this); */

            logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(@NonNull Preference preference) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("로그아웃").setMessage("로그아웃하시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 확인 버튼을 누를 경우 로그아웃 수행
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
                    return false;
                }
            });

        }

        // 클릭 이벤트 지정
        @Override
        public boolean onPreferenceClick(final Preference preference) {
            switch (preference.getKey()) {
                case "feedback":
                    return false;
                case "logout":
                    return false;
                case "signout":
                    return false;
                case "opensource":
                    return false;
                default:
                    return false;
            }
        }
    }
}


