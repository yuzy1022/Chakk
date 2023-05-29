package com.example.chack;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        //네이티브 앱 키로 변경
        KakaoSdk.init(this, "9beee2cee6bad7a9e615a0433ff6c819");
    }
}
