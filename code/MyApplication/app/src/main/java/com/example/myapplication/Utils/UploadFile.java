package com.example.myapplication.Utils;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface UploadFile {
    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadMultipleFiles(
            @PartMap Map<String,RequestBody> files);



}
