package com.example.myapplication.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.myapplication.Adapter.ReportAdapter;
import com.example.myapplication.Bean.Report;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Constant;
import com.example.myapplication.Utils.DateFormatUtils;
import com.example.myapplication.Utils.NetRequestService;
import com.example.myapplication.View.TitleLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewReportActivity extends AppCompatActivity {
    private List<Report> reportList = new ArrayList<>();
    private TitleLayout titleLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        titleLayout = findViewById(R.id.title);
        titleLayout.setTitle("查看报告");
        titleLayout.setNextGone();
        titleLayout.setOnBackClickListener(new TitleLayout.OnBackClickListener() {
            @Override
            public void onMenuBackClick() {
                finish();
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recycle_view);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constant.QUERYDISEASE_URL)
                .build();
        NetRequestService netRequestService =retrofit.create(NetRequestService.class);
        final Call<ResponseBody> call= netRequestService.getReport(LoginActivity.sp.getString("token",""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String body = response.body().string();
                                JSONObject jsonObject = new JSONObject(body);
                                int code=jsonObject.getInt("code");
                                String message=jsonObject.getString("msg");
                                if(code==200){
                                    JSONObject pressure=jsonObject.getJSONObject("pressure");
                                    JSONObject oxygen=jsonObject.getJSONObject("oxygen");
                                    int cuff_pressure=pressure.getInt("cuffPressure");
                                    int sys=pressure.getInt("systolicPressure");
                                    int dia=pressure.getInt("diastolicPressure");
                                    int hr1=pressure.getInt("heartRate");
                                    String date1=pressure.getString("recordTime");
                                    String date11=date1.replaceAll("/","-");
                                    int oxy=oxygen.getInt("bloodOxygen");
                                    double pi=oxygen.getDouble("pi");
                                    int res=oxygen.getInt("resvalue");
                                    int hr2=oxygen.getInt("heartRate");
                                    String date2=oxygen.getString("recordTime");
                                    String date22=date2.replaceAll("/","-");
                                    long time1= DateFormatUtils.str2Long(date11,true);
                                    long time2=DateFormatUtils.str2Long(date22,true);
                                    Report report,report1,report2,report3,report4,report5,report6,report7;
                                    if(time1<time2){
                                        report=new Report("心率",String.valueOf(hr2),date2);
                                        report1=new Report("血氧",String.valueOf(oxy),date2);
                                        report2=new Report("pi",String.valueOf(pi),date2);
                                        report3=new Report("res",String.valueOf(res),date2);
                                        report4=new Report("袖带压",String.valueOf(cuff_pressure),date1);
                                        report5=new Report("收缩压",String.valueOf(sys),date1);
                                        report6=new Report("舒张压",String.valueOf(dia),date1);
                                    }else{
                                        report=new Report("心率",String.valueOf(hr1),date1);
                                        report1=new Report("袖带压",String.valueOf(cuff_pressure),date1);
                                        report2=new Report("收缩压",String.valueOf(sys),date1);
                                        report3=new Report("舒张压",String.valueOf(dia),date1);
                                        report4=new Report("血氧",String.valueOf(oxy),date2);
                                        report5=new Report("pi",String.valueOf(pi),date2);
                                        report6=new Report("res",String.valueOf(res),date2);
                                    }
                                    reportList.add(report);
                                    reportList.add(report1);
                                    reportList.add(report2);
                                    reportList.add(report3);
                                    reportList.add(report4);
                                    reportList.add(report5);
                                    reportList.add(report6);
                                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ViewReportActivity.this);
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    ReportAdapter reportAdapter=new ReportAdapter(reportList);
                                    recyclerView.setAdapter(reportAdapter);

                                }else{
                                    Toast.makeText(ViewReportActivity.this, message, Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
