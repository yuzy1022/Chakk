package com.example.chack;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class Login extends AppCompatActivity {
    private static final String TAG = "login";
    private View loginButton;
    android.widget.Button guestButton,signUp,gotoLogin;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();


        loginButton = findViewById(R.id.login);
        guestButton = findViewById(R.id.guestLoginButton);
        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
        gotoLogin = findViewById(R.id.gotoLogin);
        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Fblogin.class);
                startActivity(intent);
            }
        });
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
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("nickname", (String[]) null); // nickname 값을 인텐트에 추가
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // Name, email address, and profile photo Url
                String name = user.getDisplayName();
                String email = user.getEmail();
                Uri photoUrl = user.getPhotoUrl();

                // Check if user's email is verified
                boolean emailVerified = user.isEmailVerified();

                // The user's ID, unique to the Firebase project. Do NOT use this value to
                // authenticate with your backend server, if you have one. Use
                // FirebaseUser.getIdToken() instead.
                String uid = user.getUid();
                Log.d(TAG, "Name: " + name);
                Log.d(TAG, "Email: " + email);
                Log.d(TAG, "PhotoUrl: " + photoUrl);
                Log.d(TAG, "EmailVerfied: " + emailVerified);
                Log.d(TAG, "UID: " + uid);
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
    private void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null) {
                    // 유저 정보 가져오기
                    String nickname = user.getKakaoAccount().getProfile().getNickname();
                    Long id = user.getId();
                    String profileImageUrl = user.getKakaoAccount().getProfile().getProfileImageUrl();
                    // 로그에 출력
                    Log.d(TAG, "Nickname: " + nickname);
                    Log.d(TAG, "UserId: " + id);
                    Log.d(TAG, "Profile Image URL: " + profileImageUrl);
                    // 이미 로그인된 상태
                    Toast.makeText(Login.this, "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("nickname", nickname); // nickname 값을 인텐트에 추가
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
