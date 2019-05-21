package com.example.myapplication.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Bean.Report;
import com.example.myapplication.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<Report> mReportList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView reportItem;
        TextView reportvalue;
        TextView time;
        TextView line;
        public ViewHolder(@NonNull View view) {
            super(view);
            reportItem=(TextView)view.findViewById(R.id.item);
            reportvalue=(TextView)view.findViewById(R.id.value);
            time=view.findViewById(R.id.time);
            line=view.findViewById(R.id.line);
        }
    }

    public HistoryAdapter(List<Report> reportList){
        mReportList=reportList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_item,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Report report=mReportList.get(i);
        holder.reportItem.setText(report.getName());
        holder.reportvalue.setText(report.getValue());
        holder.time.setText(report.getTime());
        if(i==mReportList.size()-1){
            holder.line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mReportList.size();
    }

}
