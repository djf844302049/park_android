package com.anyidc.cloudpark.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.MyAppointmentBean;
import com.anyidc.cloudpark.moduel.MyShareBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

/**
 * Created by linwenxiong on 2018/3/13.
 */

public class AppointmentIngFragment extends LazyBaseFragment {
    private TextView tvParkName,tvAddress,tvDistance,tvParkNum,tvTime,tvConfirm,tvCancel;
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
                        if (data != null && data.getList() != null) {
                            MyAppointmentBean.AppointmentBean bean = data.getList().get(0);
                            updateView(bean);
                        }
                    }
                });
    }

    private void updateView(MyAppointmentBean.AppointmentBean appointmentBean){
        if(appointmentBean == null) return;
//        tvParkName.setText();
    }

    @Override
    protected void initView() {
        tvParkName = layout.findViewById(R.id.tv_park_name);
        tvAddress = layout.findViewById(R.id.tv_address);
        tvDistance = layout.findViewById(R.id.tv_distance);
        tvParkNum = layout.findViewById(R.id.tv_parking_num);
        tvTime = layout.findViewById(R.id.tv_time);
        tvConfirm = layout.findViewById(R.id.tv_confirm);
        tvCancel = layout.findViewById(R.id.tv_cancel);
    }
}
