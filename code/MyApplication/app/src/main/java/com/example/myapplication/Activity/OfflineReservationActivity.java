package com.example.myapplication.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Utils.Constant;
import com.example.myapplication.Utils.NetRequestService;
import com.example.myapplication.View.TitleLayout;

import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OfflineReservationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener ,View.OnClickListener{
    private TitleLayout titleLayout;
    private Spinner distance;
    private Spinner language;
    private Spinner sex;
    private static String dis;
    private static String lan;
    private static String ssex;
    private Button search;
    private LinearLayout time;
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView textView;
    private int diseaseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_reservation);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        textView=findViewById(R.id.time_text);
        titleLayout = findViewById(R.id.title);
        titleLayout.setTitle("线下预约");
        titleLayout.setNextGone();
        titleLayout.setOnBackClickListener(new TitleLayout.OnBackClickListener() {
            @Override
            public void onMenuBackClick() {
               finish();
            }
        });
        search=(Button)findViewById(R.id.search_button);
        search.setOnClickListener(this);
        time=findViewById(R.id.time);
        time.setOnClickListener(this);
        distance=(Spinner)findViewById(R.id.distance);
        language=(Spinner)findViewById(R.id.language);
        sex=(Spinner)findViewById(R.id.sex);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                OfflineReservationActivity.this, R.array.distance_array,
                android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(R.layout.spinner_item);
        distance.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                OfflineReservationActivity.this, R.array.language_array,
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(R.layout.spinner_item);
        language.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                OfflineReservationActivity.this, R.array.sex_array,
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(R.layout.spinner_item);
        sex.setAdapter(adapter3);


        distance.setOnItemSelectedListener(this);
        language.setOnItemSelectedListener(this);
        sex.setOnItemSelectedListener(this);
        Intent intent=getIntent();
        diseaseId=intent.getIntExtra("diseaseId",0);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.distance:
                dis =adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.language:
                lan=adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.sex:
                ssex=adapterView.getItemAtPosition(i).toString();
                break;
                default:
                    break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_button:
                Intent intent=new Intent(OfflineReservationActivity.this,OfflineDoctorActivity.class);
                intent.putExtra("time",textView.getText().toString());
                intent.putExtra("diseaseId",diseaseId);
                startActivity(intent);
                break;
            case R.id.time:
                Calendar calendar=Calendar.getInstance();
                mYear=calendar.get(Calendar.YEAR);
                mMonth=calendar.get(Calendar.MONTH);
                mDay=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(OfflineReservationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear=year;
                        mMonth=month;
                        mDay=dayOfMonth;
                        textView.setText(String.valueOf(mYear)+"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mDay));
                    }
                },mYear,mMonth,mDay);
                dialog.show();
                break;
        }

    }
}
