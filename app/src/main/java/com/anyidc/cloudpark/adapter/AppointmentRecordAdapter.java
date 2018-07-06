package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.utils.LogUtils;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.MyAppointmentBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by linwenxiong on 2018/3/30.
 */

public class AppointmentRecordAdapter extends RecyclerView.Adapter<AppointmentRecordAdapter.AppointmentHolderView> {
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<MyAppointmentBean.AppointmentBean> dataList = new ArrayList<>();

    public AppointmentRecordAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void clear() {
        dataList.clear();
    }

    public void addList(List<MyAppointmentBean.AppointmentBean> list) {
        if (list != null) {
            dataList.addAll(list);
        }
    }

    @Override
    public AppointmentHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.appointment_record_item_layout, null, false);
        return new AppointmentHolderView(itemView);
    }

    @Override
    public void onBindViewHolder(AppointmentHolderView holder, int position) {
        MyAppointmentBean.AppointmentBean appointmentBean = dataList.get(position);
        if (appointmentBean != null) {
            holder.tvOrderNum.setText("订单号：" + appointmentBean.getOrder_sn());
            String dateStr = stampToDate(appointmentBean.getCreate_time());
            holder.tvCreateTime.setText(dateStr);
            //订单日期
            String[] dateArr = dateStr.split(" ");
            LogUtils.e("dateStr = " + dateStr);
            if (dateArr != null && dateArr.length > 0) {
                holder.tvDate.setText(dateArr[0]);
            }
            //预约时间
            String appointmentStr = stampToDate(appointmentBean.getPay_time() + appointmentBean.getTimes());
            String[] appointmentArr = appointmentStr.split(" ");
            if (appointmentArr != null && appointmentArr.length > 1) {
                holder.tvAppointmentTime.setText("预约时间：" + appointmentArr[1]);
            }
            //到达时间
            String arriveStr = stampToDate(appointmentBean.getEnd_time());
            String[] arriveArr = arriveStr.split(" ");
            if (arriveArr != null && arriveArr.length > 1) {
                switch (appointmentBean.getStatus()) {
                    case 3:
                        holder.tvCancel.setVisibility(View.GONE);
                        holder.tvArriveTime.setText("到达时间：" + arriveArr[1]);
                        break;
                    case 4:
                        holder.tvArriveTime.setText("取消时间：" + arriveArr[1]);
                        holder.tvCancel.setVisibility(View.VISIBLE);
                        break;
                }
            }

            if (appointmentBean.getPark() != null) {
                holder.tvParkName.setText(appointmentBean.getPark().getParking_name());
            }
        }
    }

    private static String stampToDate(long s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(s * 1000);
        res = simpleDateFormat.format(date);
        return res;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class AppointmentHolderView extends RecyclerView.ViewHolder {
        private TextView tvOrderNum, tvCreateTime, tvParkName, tvDate, tvAppointmentTime, tvArriveTime, tvCancel;

        public AppointmentHolderView(View itemView) {
            super(itemView);
            tvOrderNum = itemView.findViewById(R.id.tv_order_num);
            tvCreateTime = itemView.findViewById(R.id.tv_order_time);
            tvParkName = itemView.findViewById(R.id.tv_park_name);
            tvDate = itemView.findViewById(R.id.tv_park_date);
            tvAppointmentTime = itemView.findViewById(R.id.tv_appointment_time);
            tvArriveTime = itemView.findViewById(R.id.tv_arrive_time);
            tvCancel = itemView.findViewById(R.id.tv_cancel);
        }
    }
}
