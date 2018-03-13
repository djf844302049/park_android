package com.anyidc.cloudpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.StopRecordBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/4.
 */

public class StopRecordAdapter extends RecyclerView.Adapter<StopRecordAdapter.RecordViewHolder> {
    private List<StopRecordBean.OrderBean> list;

    public StopRecordAdapter(List<StopRecordBean.OrderBean> list) {
        this.list = list;
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_stop_record, parent, false);
        return new RecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        StopRecordBean.OrderBean orderBean = list.get(position);
        Date parse = new Date(orderBean.getCreate_time());
        Date parse1 = new Date(orderBean.getPay_time());
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(parse);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(parse);
        holder.tvOrderNum.setText("订单号：" + orderBean.getOrder_sn());
        holder.tvParkName.setText(orderBean.getParking_name());
        holder.tvDateTime.setText(dateTime);
        holder.tvDate.setText(date);
        String duration = new SimpleDateFormat("HH:mm").format(parse) +
                "-" + new SimpleDateFormat("HH:mm").format(parse1);
        holder.tvTime.setText(duration);
        holder.tvPrice.setText("￥" + orderBean.getTotal_amount());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderNum;
        TextView tvDateTime;
        TextView tvParkName;
        TextView tvDate;
        TextView tvTime;
        TextView tvPrice;

        public RecordViewHolder(View itemView) {
            super(itemView);
            tvOrderNum = itemView.findViewById(R.id.tv_order_num);
            tvDateTime = itemView.findViewById(R.id.tv_date_time);
            tvParkName = itemView.findViewById(R.id.tv_park_name);
            tvDate = itemView.findViewById(R.id.tv_park_date);
            tvTime = itemView.findViewById(R.id.tv_park_time);
            tvPrice = itemView.findViewById(R.id.tv_park_price);
        }
    }
}
