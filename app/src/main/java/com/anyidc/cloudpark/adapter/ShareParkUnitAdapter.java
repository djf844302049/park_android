package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.ShareParkUnitInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/11.
 */

public class ShareParkUnitAdapter extends RecyclerView.Adapter<ShareParkUnitAdapter.ShareParkUnitHolderView> implements View.OnClickListener{
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<ShareParkUnitInfo> sharelist;
    private ItemClickListener itemClickListener;
    public ShareParkUnitAdapter(Context context,ArrayList<ShareParkUnitInfo> dataList){
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.sharelist = dataList;
    }


    @Override
    public ShareParkUnitHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.share_park_unit_item_layout,null,false);
        return new ShareParkUnitHolderView(itemView);
    }

    @Override
    public void onBindViewHolder(ShareParkUnitHolderView holder, int position) {
        ShareParkUnitInfo shareParkUnitInfo = sharelist.get(position);
        if(shareParkUnitInfo != null){
            holder.itemView.setTag(position);
            holder.tvNum.setText(shareParkUnitInfo.getUnit_id());
            holder.tvShareTime.setText("共享时间段："+shareParkUnitInfo.getShare_time());
            holder.tvFee.setText("停车费：¥"+shareParkUnitInfo.getFee().getMoney());
            holder.itemView.setOnClickListener(this);
            holder.ivCheck.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return sharelist == null? 0 : sharelist.size();
    }

    @Override
    public void onClick(View view) {
        int position = (Integer)view.getTag();
        if(itemClickListener != null){
            itemClickListener.onItemClick(view,position);
        }
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public class ShareParkUnitHolderView extends RecyclerView.ViewHolder{
        private TextView tvNum,tvShareTime,tvFee;
        private ImageView ivCheck;

        public ShareParkUnitHolderView(View itemView){
            super(itemView);
            tvNum = itemView.findViewById(R.id.tv_unit_num);
            ivCheck = itemView.findViewById(R.id.iv_check);
            tvShareTime = itemView.findViewById(R.id.tv_share_time);
            tvFee = itemView.findViewById(R.id.tv_unit_fee);
        }
    }
}
