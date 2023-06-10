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
    Call<DataClass> getSearchBook(  //상품검색
            @Query("ttbkey") String ttbkey,  //ttb키 입력
            @Query("Query") String Query, //검색할 검색어
            @Query("QueryType") String QueryType, //검색어 종류 (Keyword : 제목+저자, Title : 제목, Author : 저자)
            @Query("SearchTarget") String searchTarget, //검색대상 (Book으로 입력)
            @Query("MaxResults") String maxResult,  //검색 결과 최대 출력 개수 (최대 100)
            @Query("output") String output,  //출력 방법 (js로 입력)
            @Query("Version") String version  //버전 (20131101 입력)
    );

    @GET("ItemLookUp.aspx")
    Call<DataClass> getISBNSearchBook( //상품 조회
            @Query("ttbkey") String ttbkey, //ttb키 입력
            @Query("ItemId") String itemId, // ItemIdType에서 정한 타입으로 검색할 값 입력
            @Query("ItemIdType") String itemIdType, //isbn, isbn13 중 택
            @Query("output") String output,  //출력 방법 (js로 입력)
            @Query("Version") String version  //버전 (20131101 입력)
    );
}
