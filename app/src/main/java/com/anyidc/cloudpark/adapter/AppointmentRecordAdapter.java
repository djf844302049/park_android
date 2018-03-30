package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.MyAppointmentBean;

import java.util.ArrayList;

/**
 * Created by linwenxiong on 2018/3/30.
 */

public class AppointmentRecordAdapter extends RecyclerView.Adapter<AppointmentRecordAdapter.AppointmentHolderView>{
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<MyAppointmentBean.AppointmentBean> dataList = new ArrayList<>();
    public AppointmentRecordAdapter(Context context){
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public AppointmentHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.appointment_record_item_layout,null,false);
        return new AppointmentHolderView(itemView);
    }

    @Override
    public void onBindViewHolder(AppointmentHolderView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class AppointmentHolderView extends RecyclerView.ViewHolder{
        private TextView tvParkNum,tvCreateTime,tvParkName,tvDate,tvAppointmentTime,tvArriveTime;
        public AppointmentHolderView(View itemView){
            super(itemView);
            tvParkNum = itemView.findViewById(R.id.tv_order_num);
            tvCreateTime = itemView.findViewById(R.id.tv_order_time);
            tvParkName = itemView.findViewById(R.id.tv_park_name);
            tvDate = itemView.findViewById(R.id.tv_order_time);
            tvAppointmentTime = itemView.findViewById(R.id.tv_appointment_time);
            tvArriveTime = itemView.findViewById(R.id.tv_arrive_time);
        }
    }
}
