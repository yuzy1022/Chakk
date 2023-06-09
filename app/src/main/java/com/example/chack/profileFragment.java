package com.example.chack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class profileFragment extends Fragment {

    private TextView profileIdTextView;
    private String nickname;

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

        profileIdTextView = view.findViewById(R.id.profileId);
        if (nickname != null) {
            String profileId = "ID: " + nickname;
            profileIdTextView.setText(profileId);
        }

        TextView logoutButton = view.findViewById(R.id.logOut);
        TextView accountRemove = view.findViewById(R.id.accountRemove);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        accountRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        return view;
    }

}