package com.example.chack;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SettingFragment extends PreferenceFragment {

    SharedPreferences pref;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // 별도의 화면 레이아웃파일을 사용하지않고
        // 설정 xml문서를 통해 화면이 자동생성
        // PregerenceScreen 클래스를 통해 화면설계
        // 앱이 종료되도 최종 설정값 유지
        addPreferencesFromResource(R.xml.setting);

        //SharedPreferences객체를 참조하여 설정상태에 대한 제어 가능
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //각 key값에 해당하는 설정 저장값 가져오기
        //  boolean isVibrate = pref.getBoolean("vibrate",false);
        // Toast.makeText(getActivity(),"진동알림이 켜졌습니다."+isVibrate,Toast.LENGTH_SHORT).show();
    }
}
