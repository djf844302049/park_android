package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.activity.OptParkLockActivity;
import com.anyidc.cloudpark.moduel.MyShareBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/18.
 */

public class MyShareAdapter extends RecyclerView.Adapter<MyShareAdapter.ShareParkViewHolder>{

    private Context mContext;
    private List<MyShareBean.ShareParkBean> dataList = new ArrayList<>();

    public MyShareAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void updateList(List<MyShareBean.ShareParkBean> dataList){
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
        final MyShareBean.ShareParkBean shareParkBean = dataList.get(position);
        if(shareParkBean != null){
            if(shareParkBean.getPark() != null){
                holder.tvAddress.setText(shareParkBean.getPark().getArea_1() + " " + shareParkBean.getPark().getArea_2() + " " + shareParkBean.getPark().getArea_3() + " " + shareParkBean.getPark().getArea_4() + " " );
            }else {
                holder.tvAddress.setText("");
            }

            if(shareParkBean.getPark() != null){
                holder.tvName.setText(shareParkBean.getPark().getParking_name());
            }else {
                holder.tvName.setText("");
            }
            holder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(shareParkBean.getStatus() == 1) {//只有在空闲的时候才能进行操作
                        toOptParkLock(shareParkBean);
                    }
                }
            });

            holder.tvNum.setText(String.format(mContext.getString(R.string.park_num), shareParkBean.getParking_id()));
        }
    }

    /**
     * 操作车位锁
     * @param shareParkBean
     */
    private void toOptParkLock(MyShareBean.ShareParkBean shareParkBean){
        OptParkLockActivity.start(mContext,String.valueOf(shareParkBean.getParking_id()),OptParkLockActivity.FROMSHAREOWNER);
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
