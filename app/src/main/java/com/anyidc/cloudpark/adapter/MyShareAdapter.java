package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/3/18.
 */

public class MyShareAdapter extends RecyclerView.Adapter<MyShareAdapter.ShareParkViewHolder>{

    private Context mContext;
    public MyShareAdapter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public ShareParkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.my_share_item_layout,null,false);
        return new ShareParkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShareParkViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ShareParkViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout llItem;
        private TextView tvName,tvNum,tvAddress;
        public ShareParkViewHolder(View itemView){
            super(itemView);
            llItem = itemView.findViewById(R.id.ll_item);
            tvName = itemView.findViewById(R.id.tv_park_name);
            tvNum = itemView.findViewById(R.id.tv_parking_num);
            tvAddress = itemView.findViewById(R.id.tv_address);
        }
    }
}
