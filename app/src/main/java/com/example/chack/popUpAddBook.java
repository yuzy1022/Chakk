package com.example.chack;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class popUpAddBook extends AppCompatActivity {
    FrameLayout frame;
    private TabLayout mTabLayout;
    Button submit, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_addbook);
        cancel = findViewById(R.id.cancel);
        submit = findViewById(R.id.apply);
        init(); //네비게이션 버튼, 객체 생성

        //TODO 추후 DB 연결, 내서재들에 추가하기
        submit.setOnClickListener((v -> {finish();}));
        cancel.setOnClickListener((v -> {finish();}));
        //페이지마다 액션바 이름 바꾸기
        // 첫 번째 탭 선택
        mTabLayout.getTabAt(0).select();

        // 프래그먼트 추가
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, new addBookPastFragment());
        fragmentTransaction.commit();

        // 탭 선택시 이벤트 처리
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selectedFragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        selectedFragment = new addBookPastFragment();
                        break;
                    case 1:
                        selectedFragment = new addBookCurrentFragment();
                        break;
                    case 2:
                        selectedFragment = new addBookFutureFragment();
                        break;
                }

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, selectedFragment);
                fragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
    private void init() {
        frame = findViewById(R.id.frame);
        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("읽은 책"));
        mTabLayout.addTab(mTabLayout.newTab().setText("읽고 있는 책"));
        mTabLayout.addTab(mTabLayout.newTab().setText("읽고 싶은 책"));
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}