package com.example.myapplication.Activity;

import android.os.Environment;
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
import com.example.myapplication.Utils.ReportService;
import com.example.myapplication.View.TitleLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
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
                .baseUrl(Constant.LOADREPORT_URL)
                .build();
        ReportService reportService=retrofit.create(ReportService.class);
        final Call<ResponseBody> call=reportService.getReport(LoginActivity.sp.getString("token",""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String body = response.body().toString();
                                JSONObject jsonObject = new JSONObject(body);
                                int code=jsonObject.getInt("code");
                                String message=jsonObject.getString("message");
                                JSONArray array=jsonObject.getJSONArray("item");
                                if(code==200){
                                    for(int i=0;i<array.length();i++){
                                        JSONObject object=array.getJSONObject(i);
                                        Report report=new Report(object.getString("name"),object.getString("value"),
                                                object.getString("recordTime"));
                                        reportList.add(report);
                                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ViewReportActivity.this);
                                        recyclerView.setLayoutManager(linearLayoutManager);
                                        ReportAdapter reportAdapter=new ReportAdapter(reportList);
                                        recyclerView.setAdapter(reportAdapter);
                                    }

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

//    private void init(){
//        Report report1=new Report("血压","108","dsad");
//        Report report2=new Report("心率","68","2018/03/01");
//        Report report3=new Report("体温","36.8","dsdf");
//
//        reportList.add(report1);
//        reportList.add(report2);
//        reportList.add(report3);
//    }

}
