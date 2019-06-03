package com.example.myapplication.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.HistoryAdapter;
import com.example.myapplication.Bean.Report;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Constant;
import com.example.myapplication.Utils.NetRequestService;
import com.example.myapplication.View.LineView;
import com.example.myapplication.View.TitleLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewHistoryActivity extends AppCompatActivity {
    private List<Report> reportList = new ArrayList<>();
    private TitleLayout titleLayout;
    private String name;
    private RecyclerView recyclerView;
    private LineView lineView;
    private List<Float> yList=new ArrayList<>();
    private List<String> xList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Intent intent = getIntent();
        name = intent.getStringExtra("itemName");
        titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(name);
        titleLayout.setNextGone();
        titleLayout.setOnBackClickListener(new TitleLayout.OnBackClickListener() {
            @Override
            public void onMenuBackClick() {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        lineView=findViewById(R.id.lineView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.LOADREPORT_URL)
                .build();
        NetRequestService netRequestService = retrofit.create(NetRequestService.class);
        Call<ResponseBody> call;
        if (name.equals("袖带压") || name.equals("收缩压") || name.equals("舒张压")) {
            call = netRequestService.getBpHistory(LoginActivity.sp.getString("token", ""),7);
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
                                    int code = jsonObject.getInt("code");
                                    String message = jsonObject.getString("msg");
                                    if (code == 0) {
                                        JSONArray array = jsonObject.getJSONArray("data");
                                        Report report;
                                        if (name.equals("袖带压")) {
                                            for (int i = 0; i < array.length(); i++) {
                                                JSONObject object = array.getJSONObject(i);
                                                report = new Report(name, String.valueOf(object.getInt("cuffPressure")),
                                                        object.getString("recordTime"));
                                                reportList.add(report);
                                                xList.add(object.getString("recordTime").substring(5,10));
                                                yList.add((float) object.getInt("cuffPressure"));
                                            }
                                            Collections.reverse(xList);
                                            Collections.reverse(yList);
                                            lineView.setData(xList,yList);

                                        } else if (name.equals("收缩压")) {
                                            for (int i = 0; i < array.length(); i++) {
                                                JSONObject object = array.getJSONObject(i);
                                                report = new Report(name,String.valueOf(object.getInt("systolicPressure")),
                                                        object.getString("recordTime"));
                                                reportList.add(report);
                                                xList.add(object.getString("recordTime").substring(5,10));
                                                yList.add((float) object.getInt("systolicPressure"));
                                            }
                                            Collections.reverse(xList);
                                            Collections.reverse(yList);
                                            lineView.setData(xList,yList);
                                        } else {
                                            for (int i = 0; i < array.length(); i++) {
                                                JSONObject object = array.getJSONObject(i);
                                                report = new Report(name,String.valueOf(object.getInt("diastolicPressure")),
                                                        object.getString("recordTime"));
                                                reportList.add(report);
                                                xList.add(object.getString("recordTime").substring(5,10));
                                                yList.add((float) object.getInt("diastolicPressure"));
                                            }
                                            Collections.reverse(xList);
                                            Collections.reverse(yList);
                                            lineView.setData(xList,yList);
                                        }
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewHistoryActivity.this);
                                        recyclerView.setLayoutManager(linearLayoutManager);
                                        HistoryAdapter reportAdapter = new HistoryAdapter(reportList);
                                        recyclerView.setAdapter(reportAdapter);

                                    } else {
                                        Toast.makeText(ViewHistoryActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(ViewHistoryActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(ViewHistoryActivity.this, "与服务器连接异常，Error！", Toast.LENGTH_SHORT).show();

                }
            });

        } else {
            call = netRequestService.getOxyHistory(LoginActivity.sp.getString("token", ""),7);
            Log.d("dsadsadsa", "onCreate: ++"+LoginActivity.sp.getString("token", ""));

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String body = response.body().string();
                                    Log.d("dsadsadsadas", "run: "+body);
                                    JSONObject jsonObject = new JSONObject(body);
                                    int code = jsonObject.getInt("code");
                                    String message = jsonObject.getString("msg");
                                    if (code == 0) {
                                        JSONArray array = jsonObject.getJSONArray("data");
                                        Report report;
                                        if (name.equals("血氧")) {
                                            for (int i = 0; i < array.length(); i++) {
                                                JSONObject object = array.getJSONObject(i);
                                                report = new Report(name, String.valueOf(object.getInt("bloodOxygen")),
                                                        object.getString("recordTime"));
                                                reportList.add(report);
                                                xList.add(object.getString("recordTime").substring(5,10));
                                                yList.add((float) object.getInt("bloodOxygen"));
                                                Log.d("dsadsadds", "run: "+(float) object.getInt("bloodOxygen"));
                                            }
                                            Collections.reverse(xList);
                                            Collections.reverse(yList);
                                            lineView.setData(xList,yList);
                                        } else if (name.equals("pi")) {
                                            for (int i = 0; i < array.length(); i++) {
                                                JSONObject object = array.getJSONObject(i);
                                                report = new Report(name, String.valueOf(object.getDouble("pi")),
                                                        object.getString("recordTime"));
                                                reportList.add(report);
                                                xList.add(object.getString("recordTime").substring(5,10));
                                                yList.add((float)object.getDouble("pi"));
                                            }
                                            for (int i=0;i<xList.size();i++){
                                                Log.d("dsadsadsa", "run: "+ xList.get(i)+" "+yList.get(i));
                                            }
                                            Collections.reverse(xList);
                                            Collections.reverse(yList);
                                            lineView.setData(xList,yList);
                                        } else if (name.equals("res")){
                                            for (int i = 0; i < array.length(); i++) {
                                                JSONObject object = array.getJSONObject(i);
                                                report = new Report(name, String.valueOf(object.getInt("resvalue")),
                                                        object.getString("recordTime"));
                                                reportList.add(report);
                                                xList.add(object.getString("recordTime").substring(5,10));
                                                yList.add((float)object.getInt("resvalue"));
                                            }
                                            Collections.reverse(xList);
                                            Collections.reverse(yList);
                                            for (int i=0;i<xList.size();i++){
                                                Log.d("dsadsadsa", "run: "+ xList.get(i)+" "+yList.get(i));
                                            }
                                            lineView.setData(xList,yList);
                                        }
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewHistoryActivity.this);
                                        recyclerView.setLayoutManager(linearLayoutManager);
                                        HistoryAdapter reportAdapter = new HistoryAdapter(reportList);
                                        recyclerView.setAdapter(reportAdapter);

                                    } else {
                                        Toast.makeText(ViewHistoryActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(ViewHistoryActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(ViewHistoryActivity.this, "与服务器连接异常，Error！", Toast.LENGTH_SHORT).show();

                }
            });

        }


    }
}
