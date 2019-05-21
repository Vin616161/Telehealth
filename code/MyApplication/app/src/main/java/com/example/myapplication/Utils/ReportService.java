package com.example.myapplication.Utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReportService {
    @GET("loadReport")
    Call<ResponseBody> getReport(@Query("access_token") String token);
    @GET("getHistoryRecord")
    Call<ResponseBody> getHistory(@Query("name") String name,@Query("access_token") String token);

}
