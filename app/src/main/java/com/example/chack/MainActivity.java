package com.example.chack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    FrameLayout frame;
    BottomNavigationView bottomNavigationView;
    String nickname; // nickname 멤버 변수 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 인텐트로부터 nickname 값을 받아옴
        Intent intent = getIntent();
        nickname = intent.getStringExtra("nickname");

        init(); //네비게이션 버튼, 객체 생성
        SettingListener(); //네비게이션 버튼 설정

        bottomNavigationView.setSelectedItemId(R.id.tab_home); // 처음 화면으로 tab_home 선택

        profileFragment fragment = profileFragment.newInstance(nickname);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
        //페이지마다 액션바 이름 바꾸기
    }

    private void init() {
        frame = findViewById(R.id.frame);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void SettingListener() {
        bottomNavigationView.setOnItemSelectedListener(new TabSelectedLister());
    }

    class TabSelectedLister implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tab_home: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new homeFragment()).commit();
                    getSupportActionBar().setTitle(R.string.fragHome);
                    return true;
                }
                case R.id.tab_sns: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new snsFragment()).commit();
                    getSupportActionBar().setTitle(R.string.actionSnsName);
                    return true;
                }
                case R.id.tab_record: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, new recordFragment()).commit();
                    getSupportActionBar().setTitle(R.string.actionRecordName);
                    return true;
                }
                case R.id.tab_profile: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, profileFragment.newInstance(nickname)).commit();
                    getSupportActionBar().setTitle(R.string.actionProfileName);
                    return true;
                }
            }
            return false;
        }
    }

    //홈 화면과 서가채우기 화면전환
    public void onFragmentChange(int index) {
        if (index == 0) {//0일 경우 홈화면으로
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new homeFragment()).commitAllowingStateLoss();
        } else if (index == 1) {//1일 경우 서가검색으로
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new addBookFragment()).commitAllowingStateLoss();
        } else if (index == 2) {//1일 경우 서가검색으로
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new snsFragment()).commitAllowingStateLoss();
        } else if (index == 3) {//1일 경우 서가검색으로
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new writeFragment()).commitAllowingStateLoss();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);
        return true;
    }
}
