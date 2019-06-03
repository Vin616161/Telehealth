package com.example.myapplication.Utils;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface UploadFile {
    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadMultipleFiles(
            @PartMap Map<String,RequestBody> files);

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadFile(@Part("description") RequestBody body, @Part MultipartBody.Part file);
   // Call<ResponseBody> uploadFile(@Part("access_token") String token,@Part("fileType") String fileType, @Part MultipartBody.Part file);


}
