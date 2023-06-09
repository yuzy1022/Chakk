package com.example.chack;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.util.Utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GlobalApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        //네이티브 앱 키로 변경
        KakaoSdk.init(this, "9beee2cee6bad7a9e615a0433ff6c819");

    }


}
