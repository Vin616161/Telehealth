package com.example.myapplication.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.Device.DeviceScanActivity;
import com.example.myapplication.R;
import com.example.myapplication.View.TitleLayout;

public class HealthTestActivity extends AppCompatActivity implements View.OnClickListener {
    private TitleLayout titleLayout;
    private CardView scanDetection;
    private CardView viewReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_test);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        titleLayout = findViewById(R.id.title);
        titleLayout.setTitle("健康检测");
        titleLayout.setNextGone();
        titleLayout.setOnBackClickListener(new TitleLayout.OnBackClickListener() {
            @Override
            public void onMenuBackClick() {
                Intent intent=new Intent(HealthTestActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        scanDetection =findViewById(R.id.scan);
        viewReport =findViewById(R.id.history);
        scanDetection.setOnClickListener(this);
        viewReport.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.history:
                Intent intent = new Intent(HealthTestActivity.this, ViewReportActivity.class);
                startActivity(intent);
                break;
            case R.id.scan:
                Intent intent1 = new Intent(HealthTestActivity.this, DeviceScanActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent=new Intent(HealthTestActivity.this,MainActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
