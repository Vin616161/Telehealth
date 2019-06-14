package com.example.myapplication.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Activity.LoginActivity;
import com.example.myapplication.Bean.Question;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Constant;
import com.example.myapplication.Utils.UploadFile;
import com.example.myapplication.View.RecordButton;
import com.example.myapplication.View.TextArea;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HealthQuestionAdapter extends RecyclerView.Adapter<HealthQuestionAdapter.ViewHolder> {
    List<Question> mQuestionList;

    private MediaPlayer player;
    private Context mContext;
    private List<Integer> idList=new ArrayList<>();

    public HealthQuestionAdapter(List<Question> questionList, Context context) {
        mQuestionList=questionList;
        mContext=context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView question;
        TextArea answer;
        RecordButton recordButton;
        LinearLayout bofang;

        public ViewHolder(View itemView) {
            super(itemView);
            question=(TextView)itemView.findViewById(R.id.question);
            answer=(TextArea)itemView.findViewById(R.id.answer);
            recordButton=itemView.findViewById(R.id.recordButton);
            bofang=itemView.findViewById(R.id.bofang);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.health_question_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String question=mQuestionList.get(position).getQuestion();
        int questionNo=mQuestionList.get(position).getQuestionNo();
        holder.question.setText(questionNo+"、"+question);
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/audio_"+position+".mp3";
        Log.d("hhhhhhhhhhdsds", "onBindViewHolder: "+path);
        File file=new File(path);
        file.delete();
        holder.recordButton.setSavePath(path);
        holder.recordButton.setOnFinishedRecordListener(new RecordButton.OnFinishedRecordListener() {
            @Override
            public void onFinishedRecord(String audioPath) {
                Log.i("RECORD!!!", "finished!!!!!!!!!! save to " + audioPath);
                int temp=0;
                while(temp==0){
                    temp=upload(position);
                }
                idList.add(temp);
                holder.bofang.setVisibility(View.VISIBLE);
                Log.d("gfdgfdgfd", "onFinishedRecord: "+temp);
            }
        });
        player=new MediaPlayer();
        holder.bofang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player!=null){
                    player.reset();
                }else {
                    player=new MediaPlayer();
                }
                try {
                    player.setDataSource(path);
                    //初始化
                    player.prepare();
                    //开始播放
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public int upload(int i){
        int id=0;
        Map<String, RequestBody> map = new HashMap<>();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constant.UPLOAD_URL)
                .build();
        UploadFile service=retrofit.create(UploadFile.class);
        String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/audio_"+i+".mp3";
        File file=new File(path);
        String token= LoginActivity.sp.getString("token","");
        map.put("access_token",RequestBody.create(MediaType. parse("text/plain"),token));
        map.put("fileType",RequestBody.create(MediaType. parse("text/plain"), "Record"));
        map.put("file\"; filename=\""+ file.getName(),RequestBody.create(MediaType.parse("audio/*"),file));
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
}
