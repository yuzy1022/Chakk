package com.example.chack;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AladinHttpRequest {

    // @GET( EndPoint-자원위치(URI) )
    // DataClass > 요청 GET에 대한 응답데이터를 받아서 DTO 객체화할 클래스 타입 지정
    // getName > 메소드 명. 자유롭게 설정 가능, 통신에 영향x
    // @Path("post") String post > 매개변수. 매개변수 post가 @Path("post")를 보고 @GET 내부 {post}에 대입
    @GET("ItemSearch.aspx")
    Call<DataClass> getSearchBook(
            @Query("ttbkey") String ttbkey,
            @Query("Query") String Query,
            @Query("SearchTarget") String searchTarget,
            @Query("output") String output,
            @Query("Version") String version
    );

    Call<DataClass> getISBNSearchBook(
            @Query("ttbkey") String ttbkey,
            @Query("Query") String Query,
            @Query("output") String output,
            @Query("Version") String version,
            @Query("ItemIdType") String itemIdType
    );
}
