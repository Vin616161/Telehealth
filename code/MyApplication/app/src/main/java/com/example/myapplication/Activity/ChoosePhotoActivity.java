package com.example.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.myapplication.Adapter.GridViewAdapter;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Constant;
import com.example.myapplication.Utils.GlideLoader;
import com.example.myapplication.Utils.UploadFile;
import com.example.myapplication.View.BottomLayout;
import com.example.myapplication.View.TitleLayout;
import com.lcw.library.imagepicker.ImagePicker;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChoosePhotoActivity extends AppCompatActivity {
    private static final int REQUEST_SELECT_IMAGES_CODE = 0x01;

    private  GridView gridView;
    private GridViewAdapter mGridViewAddImgAdapter;
    private Context mContext;
    private ArrayList<String> mPicList = new ArrayList<>();
    private List<Integer> idList=new ArrayList<>();
    private TitleLayout titleLayout;
    private BottomLayout bottomLayout;
    private int num;
    private int diseaseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);
        mContext=this;
        ActionBar actionBar=getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        Intent intent=getIntent();
        final int type=intent.getIntExtra("type",2);
        num=intent.getIntExtra("num",0);
        diseaseId=intent.getIntExtra("DiseaseId",0);

        titleLayout = findViewById(R.id.title);
        if (type==2){
            titleLayout.setTitle("远程医疗");
        }else{
            titleLayout.setTitle("线下预约");
        }
        titleLayout.setOnBackClickListener(new TitleLayout.OnBackClickListener() {
            @Override
            public void onMenuBackClick() {
                finish();
            }
        });
        titleLayout.setOnNextClickListener(new TitleLayout.OnNextClickListener() {
            @Override
            public void onMenuNextClick() {
                for (int i=0;i<mPicList.size();i++) {
                    int temp=0;
                    while(temp==0) {
                        temp=upload(mPicList.get(i));
                    }
                    Log.d("fdsfsdfsd", "onMenuNextClick: "+temp);
                    idList.add(temp);
                }
                switch (type){
                    case 2:
                        Intent intent=new Intent(ChoosePhotoActivity.this,TeleReservationActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        Intent intent2=new Intent(ChoosePhotoActivity.this,OfflineReservationActivity.class);
                        startActivity(intent2);
                        break;
                        default:
                            break;
                }
            }
        });
        bottomLayout=findViewById(R.id.bottom_bar);
        bottomLayout.setMode(type);

        gridView=findViewById(R.id.gridView);
        initGridView();

    }

    public List<Integer> getIdList() {
        return idList;
    }

    public int upload(String path){
        int id=0;
        Map<String, RequestBody> map = new HashMap<>();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constant.UPLOAD_URL)
                .build();
        UploadFile service=retrofit.create(UploadFile.class);
        File file=new File(path);
        String token=LoginActivity.sp.getString("token","");
        map.put("access_token",RequestBody.create(MediaType. parse("text/plain"),token));
        map.put("fileType",RequestBody.create(MediaType. parse("text/plain"), "image"));
        map.put("file\"; filename=\""+ file.getName(),RequestBody.create(MediaType.parse("image/*"),file));
        Call<ResponseBody> call=service.uploadMultipleFiles(map);
        try {
            Response<ResponseBody> response=call.execute();
            if (response.isSuccessful()){
                String body =response.body().string();
                JSONObject object = new JSONObject(body);
                id = object.getInt("fileId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;

    }


    private void initGridView() {
        mGridViewAddImgAdapter = new GridViewAdapter(mContext, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过9张，才能点击
                    if (mPicList.size() == Constant.MAX_SELECT_PIC_NUM) {
                        //最多添加9张图片
                        viewPluImg(position);
                    } else {
                        //添加凭证图片
                        selectPic(Constant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }

    private void viewPluImg(int position) {
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constant.IMG_LIST, mPicList);
        intent.putExtra(Constant.POSITION, position);
        startActivityForResult(intent, Constant.REQUEST_CODE_MAIN);
    }


//    public void uploadFiles(){
//        Map<String, RequestBody> files = new HashMap<>();
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl(Constant.UPLOAD_URL)
//                .build();
//        UploadFile service=retrofit.create(UploadFile.class);
//        files.put("diseaseId",RequestBody.create(MediaType.parse("text/plain"),String.valueOf(diseaseId)));
//        String token=LoginActivity.sp.getString("access_token","");
//        files.put("access_token",RequestBody.create(MediaType.parse("text/plain"),token));
//        for (int i=0;i<num;i++){
//            String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/audio_"+i+".mp3";
//            list.add(path);
//            File file=new File(path);
//            files.put("file\"; filename=\""+ file.getName(),RequestBody.create(MediaType.parse("audio/*"),file));
//        }
//        for (int j=0;j<mPicList.size();j++){
//            list.add(mPicList.get(j));
//            File file=new File(mPicList.get(j));
//            files.put("file\"; filename=\""+ file.getName(),RequestBody.create(MediaType.parse("image/*"),file));
//        }
//        Call<ResponseBody> call=service.uploadMultipleFiles(files);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try{
//                            if (response.isSuccessful()) {
//                                String body = response.body().string();
//                                JSONObject object = new JSONObject(body);
//                                int id = object.getInt("fileId");
//                                Log.d("ghgfhgfhgfhf", "run: "+id);
//                            }
//
//                        }catch (Exception e){
//                            Log.d("ghgfhgfhgfhf", "expection: "+e.toString());
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//
//            }
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d("ghgfhgfhgfhf", "onFailure: ");
//
//            }
//        });
//    }

//    public void upload(){
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl(Constant.UPLOAD_URL)
//                .build();
//        UploadFile service=retrofit.create(UploadFile.class);
//        String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/audio_"+0+".mp3";
//        File file=new File(path);
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part aFile =
//                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//        String token=LoginActivity.sp.getString("token","");
//        Log.d("ghgfhgfhgfhf", token);
//        RequestBody body=new FormBody.Builder()
//                .add("access_token",token)
//                .add("fileType","record")
//                .build();
//
//        MultipartBody.Part access_token =
//                MultipartBody.Part.createFormData("access_token",token);
//        MultipartBody.Part fileType =
//                MultipartBody.Part.createFormData("fileType","record");
//        Call<ResponseBody> call=service.uploadFile(body,aFile);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        try{
//                            if (response.isSuccessful()) {
//                                String body = response.body().string();
//                                JSONObject object = new JSONObject(body);
//                                int id = object.getInt("fileId");
//                                Log.d("ghgfhgfhgfhf", "run: "+id);
//                            }
//
//                        }catch (Exception e){
//                            Log.d("ghgfhgfhgfhf", "expection: "+e.toString());
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d("ghgfhgfhgfhf", "onFailure: "+t.toString());
//
//                Log.d("ghgfhgfhgfhf", "onFailure: ");
//            }
//        });
//
//    }

    private void selectPic(int num){
        ImagePicker.getInstance()
                .setTitle("选择图片")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(true)//设置是否展示图片
                .showVideo(true)//设置是否展示视频
                .setMaxCount(num)//设置最大选择图片数目(默认为1，单选)
                .setImageLoader(new GlideLoader())//设置自定义图片加载器
                .start(ChoosePhotoActivity.this, REQUEST_SELECT_IMAGES_CODE);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==REQUEST_SELECT_IMAGES_CODE){
            ArrayList<String> newPicList = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
            mPicList.addAll(newPicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
        if (requestCode == Constant.REQUEST_CODE_MAIN && resultCode == Constant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(Constant.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
    }
}
