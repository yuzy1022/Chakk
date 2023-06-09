package com.example.chack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitAladin
{
    private static RetrofitAladin instance = null;
    private static AladinHttpRequest retrofitAPI;
    private final String BASE_URL = "https://www.aladin.co.kr/ttb/api/";

    private RetrofitAladin()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitAPI = retrofit.create(AladinHttpRequest.class);
    }

    public static RetrofitAladin getInstance()
    {
        if(instance == null)
        {
            instance = new RetrofitAladin();
        }
        return instance;
    }

    public static AladinHttpRequest getRetrofitInterface()
    {
        return retrofitAPI;
    }
}
