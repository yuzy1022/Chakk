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

import androidx.fragment.app.Fragment;

import com.example.chack.Login;
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
                builder.setTitle("공지사항").setMessage("이 부분에 공지사항 내용을 입력하세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 확인 버튼을 누를 경우 아무 동작 없이 팝업 닫기
                                dialog.dismiss();
                            }
                        })
                        .show();

                // 팝업 창 스타일을 수정합니다.
                AlertDialog alertDialog = builder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        // 확인 버튼에 스타일 적용
                        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        positiveButton.setTextColor(getResources().getColor(R.color.sub_color));

                        // 팝업 창 배경색 변경
                        Window window = alertDialog.getWindow();
                        if (window != null) {
                            window.setBackgroundDrawableResource(Integer.parseInt("#FFFFFF"));
                        }
                    }
                });
            }
        });

        appInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 팝업 창을 띄웁니다.
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("앱 정보").setMessage("이 부분에 앱 정보를 입력하세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 확인 버튼을 누를 경우 아무 동작 없이 팝업 닫기
                                dialog.dismiss();
                            }
                        })
                        .show();

                // 팝업 창 스타일을 수정합니다.
                AlertDialog alertDialog = builder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        // 확인 버튼에 스타일 적용
                        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        positiveButton.setTextColor(getResources().getColor(R.color.sub_color));

                        // 팝업 창 배경색 변경
                        Window window = alertDialog.getWindow();
                        if (window != null) {
                            window.setBackgroundDrawableResource(Integer.parseInt("#000000"));
                        }
                    }
                });
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 팝업 창을 띄웁니다.
                if(nickname!=null){
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
                // 팝업 창을 띄웁니다.
                if(nickname!=null){
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