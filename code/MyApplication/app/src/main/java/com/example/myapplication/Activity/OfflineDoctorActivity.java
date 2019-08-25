package com.example.myapplication.Activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.myapplication.Adapter.DoctorsAdapter;
import com.example.myapplication.Bean.DoctorData;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Constant;
import com.example.myapplication.Utils.NetRequestService;
import com.example.myapplication.View.TitleLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OfflineDoctorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<DoctorData> list=new ArrayList<>();
    private TitleLayout titleLayout;
    private int diseaseId;
    private String time;
    public String[] text = {
            "\n姓名：李凯\n中华医学会骨科分会骨病组委员\n擅长针灸推拿和小针刀治疗\n科室：骨科\n职务-职称：副主任医师\n坐诊时间：周一至周五、周日",
            "\n姓名：季霞\n协和不孕不育研究所首席专家\n中华医学会生殖医学分会委员\n科室：不孕不育中心\n职务-职称：主任医师\n坐诊时间：周一至周六",
            "\n姓名：叶天琼\n重庆市不孕不育临床中心主任 \n妇女健康知识巡回宣讲团讲师\n科室：不孕不育中心\n职务-职称：副主任医师\n坐诊时间：周一至周六",
            "\n姓名：徐小蓉\n中华医学会妇产科分会委员\n抗癌协会妇科肿瘤专委会全国委员\n科室：妇产科\n职务-职称：主任医师 教授 \n坐诊时间：周二至周六",
            "\n姓名：赵本书\n妇女病康复专业委员会不孕症学组委员\n多次参加全国妇产科专业学术交流\n科室：不孕不育中心\n职务-职称：主治医师\n坐诊时间：全天坐诊" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_doctor);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        titleLayout = findViewById(R.id.title);
        titleLayout.setTitle("线下预约");
        titleLayout.setNextGone();
        titleLayout.setOnBackClickListener(new TitleLayout.OnBackClickListener() {
            @Override
            public void onMenuBackClick() {
                finish();
            }
        });
        Intent intent=getIntent();
        time=intent.getStringExtra("time");
        diseaseId=intent.getIntExtra("diseaseId",0);
        //init();
        recyclerView=findViewById(R.id.recycler_view);
        GetDocList(diseaseId,time);
    }

    public void GetDocList(int diseaseId,String time) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.GETDOCTOR_URL)
                .build();
        NetRequestService netRequestService = retrofit.create(NetRequestService.class);
        Call<ResponseBody> call=netRequestService.getDocList(LoginActivity.sp.getString("token", ""),diseaseId,time,2);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                list.clear();
                                String body=response.body().string();
                                Log.d("dsadsafadsad", "run:success ");
                                JSONObject jsonObject=new JSONObject(body);
                                JSONArray array=jsonObject.getJSONArray("data");
                                for (int i=0;i<array.length();i++){
                                    JSONObject object=array.getJSONObject(i);
                                    int docId=object.getInt("docId");
                                    String name =object.getString("name");
                                    String title=object.getString("title");
                                    String attending=object.getString("attending");
                                    String introduction=object.getString("introduction");
                                    String language=object.getString("language");
                                    String text="\n姓名："+name+"\n"+
                                            "职务-职称："+title+"\n"+
                                            "擅长："+attending+"\n"+
                                            "简介："+introduction+"\n"+
                                            "语言："+language;
                                    Log.d("dsadsafadsad", "run: "+text);
                                    DoctorData doctorData=new DoctorData(docId,ContextCompat.
                                            getDrawable(OfflineDoctorActivity.this,R.drawable.doc11),text);
                                    list.add(doctorData);
                                }
                                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(OfflineDoctorActivity.this);
                                DoctorsAdapter doctorsAdapter=new DoctorsAdapter(list,2);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setAdapter(doctorsAdapter);

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }else{

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

}
