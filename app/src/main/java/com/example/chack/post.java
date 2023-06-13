package com.example.chack;

import android.media.Image;
import android.widget.TextView;

public class post {
    private String userName;
    private String mainText;
    private String tag;
    private String locationImg;
    private String address;
    private String userImg;


    public post() {}

    public String getUserImg() {   return userImg; }

    public void setUserImg(String userImg) {  this.userImg = userImg; }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLocationImg() {
        return locationImg;
    }

    public void setLocationImg(String locationImg) {
        this.locationImg = locationImg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}