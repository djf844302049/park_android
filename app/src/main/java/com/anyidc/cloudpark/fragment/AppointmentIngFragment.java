package com.anyidc.cloudpark.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.MyAppointmentBean;
import com.anyidc.cloudpark.moduel.ParkInfo;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.ToastUtil;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by linwenxiong on 2018/3/13.
 */

public class AppointmentIngFragment extends LazyBaseFragment implements View.OnClickListener {
    private TextView tvParkName, tvAddress, tvDistance, tvParkNum, tvTime, tvConfirm, tvCancel, tvTip, tvShareTime, tvNavigation;
    private long remain = 0;
    private MyAppointmentBean.AppointmentBean appointmentBean = null;

    @Override
    protected void inflaterLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.appointment_ing_layout, null, false);
    }

    @Override
    protected void onLazyLoad() {
        getMyAppointmentIng();
    }

    private void getMyAppointmentIng() {
        getTime(Api.getDefaultService().getAppointment("1", "1", "1")
                , new RxObserver<BaseEntity<MyAppointmentBean>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseEntity<MyAppointmentBean> appointmentBean) {
                        MyAppointmentBean data = appointmentBean.getData();
                        if (data != null && data.getList() != null && data.getList().size() > 0) {
                            MyAppointmentBean.AppointmentBean bean = data.getList().get(0);
                            updateView(bean);
                        } else {
                            noData();
                        }
                    }
                });
    }

    private void noData() {
        layout.findViewById(R.id.ll_content).setVisibility(View.GONE);
        tvTip.setText("您暂时没有正在进行中的预约!");
    }

    private void updateView(MyAppointmentBean.AppointmentBean appointmentBean) {
        this.appointmentBean = appointmentBean;
        if (appointmentBean == null || appointmentBean.getStatus() != 1) {
            noData();
            return;
        }
        layout.findViewById(R.id.ll_content).setVisibility(View.VISIBLE);
        if (appointmentBean.getPark() != null) {
            ParkInfo parkInfo = appointmentBean.getPark();
            tvParkName.setText(parkInfo.getParking_name());
            tvAddress.setText(parkInfo.getArea_1() + " " + parkInfo.getArea_2() + " " + parkInfo.getArea_3() + " " + parkInfo.getArea_4() + " ");
        }
        tvParkNum.setText(appointmentBean.getUnit_id());
        tvTime.setText("(" + stampToDate(appointmentBean.getPay_time() + appointmentBean.getTimes()) + ")");
        remain = System.currentTimeMillis() / 1000 - appointmentBean.getCreate_time();
        if (appointmentBean.getUnit_id().endsWith("S")) {
            layout.findViewById(R.id.ll_share).setVisibility(View.VISIBLE);
//            tvShareTime.setText();等待接口返回数据
        } else {
            layout.findViewById(R.id.ll_share).setVisibility(View.GONE);
        }
        tvShareTime.setText((appointmentBean.getShare_time()));
        if (remain < 60) {
            tvTip.setVisibility(View.VISIBLE);
            tvTip.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remain--;
                    if (remain <= 0) {
                        getMyAppointmentIng();
                    } else {
                        tvTip.postDelayed(this, 1000);
                    }
                }
            }, 1000);
        } else {
            tvTip.setVisibility(View.GONE);
        }

    }


    private static String stampToDate(long s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(s * 1000);
        res = simpleDateFormat.format(date);
        return res;
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
        tvShareTime = layout.findViewById(R.id.tv_share_time);
        tvNavigation = layout.findViewById(R.id.tv_navigation);
        tvConfirm.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvNavigation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                confirmAppointment();
                break;
            case R.id.tv_cancel:
                concelAppointment();
                break;
            case R.id.tv_navigation:
                jumpToMap();
                break;
        }
    }

    private void confirmAppointment() {
        if (appointmentBean == null) {
            return;
        }
        getTime(Api.getDefaultService().arrive(appointmentBean.getUnit_id())
                , new RxObserver<BaseEntity>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        ToastUtil.showToast("预约完成", Toast.LENGTH_SHORT);
                        noData();
                    }
                });
    }

    private void concelAppointment() {
        if (appointmentBean == null) {
            return;
        }
        getTime(Api.getDefaultService().cancelAppointment(appointmentBean.getUnit_id())
                , new RxObserver<BaseEntity>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        ToastUtil.showToast("取消成功", Toast.LENGTH_SHORT);
                        noData();
                    }
                });
    }

    private void jumpToMap() {
        try {
            Intent intent = Intent.getIntent("androidamap://viewMap?sourceApplication=我的位置&poiname=" + appointmentBean.getPark().getAddress() +
                    "&lat=" + appointmentBean.getPark().getLat() + "&lon=" + appointmentBean.getPark().getLng());
            if (isInstallMap("com.autonavi.minimap")) {
                startActivity(intent); //启动调用
            } else {
                ToastUtil.showToast("您没有安装高德地图客户端", Toast.LENGTH_SHORT);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private boolean isInstallMap(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }
}
