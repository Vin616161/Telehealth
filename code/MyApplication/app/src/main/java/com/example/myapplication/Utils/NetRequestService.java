package com.example.myapplication.Utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetRequestService {
    @GET("queryDiseases")
    Call<ResponseBody> getDisease(@Query("access_token") String token);
    @GET("loadReport")
    Call<ResponseBody> getReport(@Query("access_token") String token);
    @GET("blood_oxygen")
    Call<ResponseBody> getOxyHistory(@Query("access_token") String token,@Query("limit") int limit);
    @GET("blood_pressure")
    Call<ResponseBody> getBpHistory(@Query("access_token") String token,@Query("limit") int limit);
    @GET("getUser")
    Call<ResponseBody> getUser(@Query("access_token") String token);
    @GET("queryDocListWithCondition")
    Call<ResponseBody> getDocList(@Query("access_token") String token,@Query("departments_id") int diseaseId,@Query("date") String time,@Query("type") int type);

    @GET("queryDocInfoForApp")
    Call<ResponseBody> getDocDetail(@Query("access_token") String token,@Query("docId") int id);
}
