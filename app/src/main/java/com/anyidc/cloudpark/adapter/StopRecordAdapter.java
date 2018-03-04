package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/3/4.
 */

public class StopRecordAdapter extends RecyclerView.Adapter<StopRecordAdapter.RecordViewHolder>{
    private Context mContext;
    public StopRecordAdapter(Context mContext){
        this.mContext = mContext;
    }
    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.stop_record_item_layout,null,false);
        return new RecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class RecordViewHolder extends RecyclerView.ViewHolder{
        public RecordViewHolder(View itemView){
            super(itemView);
        }
    }
}
