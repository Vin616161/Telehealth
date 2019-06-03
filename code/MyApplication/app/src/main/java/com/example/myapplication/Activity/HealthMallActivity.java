package com.example.myapplication.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myapplication.R;
import com.example.myapplication.View.TitleLayout;

public class HealthMallActivity extends AppCompatActivity {
    private TitleLayout titleLayout;
    private CardView cardView1,cardView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_mall);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        titleLayout = findViewById(R.id.title);
        titleLayout.setTitle("健康商城");
        titleLayout.setNextGone();
        titleLayout.setOnBackClickListener(new TitleLayout.OnBackClickListener() {
            @Override
            public void onMenuBackClick() {
                Intent intent=new Intent(HealthMallActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        cardView1=findViewById(R.id.view1);
        cardView2=findViewById(R.id.view2);
        final Intent intent2=new Intent(HealthMallActivity.this,MallActivity.class);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
//        WebView webView=findViewById(R.id.webview);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent=new Intent(HealthMallActivity.this,MainActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
