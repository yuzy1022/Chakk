package com.example.chack;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class Login extends AppCompatActivity {
    private static final String TAG = "login";
    private View loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        loginButton = findViewById(R.id.login);

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken token, Throwable error) {
                if (error != null) {
                    // 로그인 실패
                    Log.e(TAG, "카카오계정으로 로그인 실패", error);
                    Toast.makeText(Login.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                } else if (token != null) {
                    // 로그인 성공
                }

                updateKakaoLoginUi();
                return null;
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(Login.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(Login.this, callback);
                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(Login.this, callback);
                }
            }
        });
    }

    private void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null) {
                    // 유저 정보 가져오기
                    String nickname = user.getKakaoAccount().getProfile().getNickname();
                    String email = user.getKakaoAccount().getEmail();
                    String profileImageUrl = user.getKakaoAccount().getProfile().getProfileImageUrl();
                    // 로그에 출력
                    Log.d(TAG, "Nickname: " + nickname);
                    Log.d(TAG, "Email: " + email);
                    Log.d(TAG, "Profile Image URL: " + profileImageUrl);
                    // 이미 로그인된 상태
                    Toast.makeText(Login.this, "이미 로그인되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 로그아웃된 상태
                    Toast.makeText(Login.this, "로그인실패", Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        });
    }
}
