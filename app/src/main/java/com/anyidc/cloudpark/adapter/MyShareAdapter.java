package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.MyShareBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/18.
 */

public class MyShareAdapter extends RecyclerView.Adapter<MyShareAdapter.ShareParkViewHolder>{

    private Context mContext;
    private List<MyShareBean.ParkBean> dataList = new ArrayList<>();

    public MyShareAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void updateList(List<MyShareBean.ParkBean> dataList){
        this.dataList.clear();
        if(dataList != null){
            this.dataList.addAll(dataList);
        }
    }

    @Override
    public ShareParkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.my_share_item_layout,null,false);
        return new ShareParkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShareParkViewHolder holder, int position) {
        MyShareBean.ParkBean parkBean = dataList.get(position);
        if(parkBean != null){
//            holder.tvAddress =
            holder.tvNum.setText(String.format(mContext.getString(R.string.park_num),parkBean.getParking_id()));
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
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
