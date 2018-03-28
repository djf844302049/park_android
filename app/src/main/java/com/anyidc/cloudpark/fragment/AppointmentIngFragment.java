package com.anyidc.cloudpark.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.MyAppointmentBean;
import com.anyidc.cloudpark.moduel.MyShareBean;
import com.anyidc.cloudpark.moduel.ParkInfo;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

import java.util.Timer;

/**
 * Created by linwenxiong on 2018/3/13.
 */

public class AppointmentIngFragment extends LazyBaseFragment implements View.OnClickListener{
    private TextView tvParkName,tvAddress,tvDistance,tvParkNum,tvTime,tvConfirm,tvCancel,tvTip;
    private long remain = 0;
    @Override
    protected void inflaterLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.appointment_ing_layout,null,false);
    }

    @Override
    protected void onLazyLoad() {
        getMyAppointmentIng();
    }

    private void getMyAppointmentIng(){
        getTime(Api.getDefaultService().getAppointment()
                , new RxObserver<BaseEntity<MyAppointmentBean>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseEntity<MyAppointmentBean> appointmentBean) {
                        MyAppointmentBean data = appointmentBean.getData();
                        if (data != null && data.getList() != null && data.getList().size() > 0) {
                            MyAppointmentBean.AppointmentBean bean = data.getList().get(0);
                            updateView(bean);
                        }
                    }
                });
    }

    private void noData(){
        layout.findViewById(R.id.ll_content).setVisibility(View.GONE);
        tvTip.setText("您暂时没有正在进行中的预约!");
    }

    private void updateView(MyAppointmentBean.AppointmentBean appointmentBean){
        if(appointmentBean == null || appointmentBean.getStatus() != 1){
            noData();
            return;
        }
        layout.findViewById(R.id.ll_content).setVisibility(View.VISIBLE);
        if(appointmentBean.getPark() != null) {
            ParkInfo parkInfo = appointmentBean.getPark();
            tvParkName.setText(parkInfo.getParking_name());
            tvAddress.setText(parkInfo.getArea_1() + " " + parkInfo.getArea_2() + " " + parkInfo.getArea_3() + " " + parkInfo.getArea_4() + " " );
        }
        tvParkNum.setText(appointmentBean.getUnit_id());
        remain = System.currentTimeMillis()/1000 - appointmentBean.getCreate_time();
        if (remain < 60) {
            tvTip.setVisibility(View.VISIBLE);
            tvTip.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remain--;
                    if(remain <= 0){
                        getMyAppointmentIng();
                    }else {
                        tvTip.postDelayed(this,1000);
                    }
                }
            },1000);
        }else{
            tvTip.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initView() {
        tvParkName = layout.findViewById(R.id.tv_park_name);
        tvAddress = layout.findViewById(R.id.tv_address);
        tvDistance = layout.findViewById(R.id.tv_distance);//距离要结合地图的api
        tvParkNum = layout.findViewById(R.id.tv_parking_num);
        tvTime = layout.findViewById(R.id.tv_time);//明天找借口
        tvConfirm = layout.findViewById(R.id.tv_confirm);
        tvCancel = layout.findViewById(R.id.tv_cancel);
        tvTip = layout.findViewById(R.id.tv_tip);
        tvConfirm.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_confirm:
                break;
            case R.id.tv_cancel:
                break;
        }
    }
}
