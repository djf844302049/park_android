package com.anyidc.cloudpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.activity.ParkChargeActivity;
import com.anyidc.cloudpark.moduel.StopRecordBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/4.
 */

public class StopRecordIngAdapter extends RecyclerView.Adapter<StopRecordIngAdapter.RecordIngViewHolder> {
    private List<StopRecordBean.OrderBean> list;

    public StopRecordIngAdapter(List<StopRecordBean.OrderBean> list) {
        this.list = list;
    }

    @Override
    public RecordIngViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_stop_record_card, parent, false);
        return new RecordIngViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecordIngViewHolder holder, int position) {
        StopRecordBean.OrderBean orderBean = list.get(position);
        holder.btnGoPay.setVisibility(View.VISIBLE);
        holder.btnGoPay.setOnClickListener(v -> ParkChargeActivity.actionStart(holder.itemView.getContext(), orderBean.getUnit_id()));
        holder.tvParkName.setText(orderBean.getParking_name());
        Date parse = new Date(orderBean.getCreate_time());
        String time = new SimpleDateFormat("HH:mm:ss").format(parse);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(parse);
        holder.tvParkDate.setText(date);
        holder.tvParkTime.setText(time);
        holder.tvParkPrice.setText("￥" + orderBean.getTotal_amount() + "(计费中)");
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class RecordIngViewHolder extends RecyclerView.ViewHolder {
        TextView tvParkName;
        TextView tvParkDate;
        TextView tvParkTime;
        TextView tvParkPrice;
        Button btnGoPay;

        public RecordIngViewHolder(View itemView) {
            super(itemView);
            tvParkName = itemView.findViewById(R.id.tv_park_name);
            tvParkDate = itemView.findViewById(R.id.tv_park_date);
            tvParkTime = itemView.findViewById(R.id.tv_park_time);
            tvParkPrice = itemView.findViewById(R.id.tv_park_price);
            btnGoPay = itemView.findViewById(R.id.btn_go_pay);
        }
    }
}
