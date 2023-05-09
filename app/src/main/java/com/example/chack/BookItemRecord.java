package com.example.chack;

import java.text.SimpleDateFormat;
import java.sql.Date;

//책정보를 담아놓을 클래스
public class BookItemRecord
{
    int image, bookPage, readPage;
    float rating;
    String name, writer;
    Date startingDate, endDate;

    //시작 날짜 설정하는 메소드
    public void setStartingDate(int y, int m, int d)
    {
        startingDate = new Date(y-1900, m-1, d);

    }

    //끝 날짜 설정하는 메소드
    public void setEndDate(int y, int m, int d)
    {
        endDate = new Date(y-1900, m-1, d);

    }

    //시작 날짜 리턴해주는 메소드
    public String getStartingDate()
    {
        SimpleDateFormat s = new SimpleDateFormat("yyyy.MM.dd");
        return s.format(startingDate);
    }

    //끝 날짜 리턴해주는 메소드
    public String getEndDate()
    {
        SimpleDateFormat s = new SimpleDateFormat("yyyy.MM.dd");
        return s.format(endDate);
    }
}
