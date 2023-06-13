package com.example.chack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {
    EditText emailEditText, passwordEditText, passwordCheckEditText;
    android.widget.Button signUpButton, gotoLoginButton;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordCheckEditText = findViewById(R.id.passwordCheckEditText);
        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordCheck = passwordCheckEditText.getText().toString();

                if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0){
                    if(password.equals(passwordCheck)){
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(Signup.this,  new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Toast.makeText(Signup.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                            myStartActivity(MainActivity.class);
                                        } else {
                                            if(task.getException() != null){
                                                Toast.makeText(Signup.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                    }else{
                        Toast.makeText(Signup.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Signup.this, "이메일 또는 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
