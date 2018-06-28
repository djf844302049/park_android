package com.anyidc.cloudpark.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.dialog.ConfirmCancelDialog;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.MyAppointmentBean;
import com.anyidc.cloudpark.moduel.ParkInfo;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.PermissionSetting;
import com.anyidc.cloudpark.utils.ToastUtil;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by linwenxiong on 2018/3/13.
 */

public class AppointmentIngFragment extends LazyBaseFragment implements View.OnClickListener, AMapLocationListener {
    private TextView tvParkName, tvAddress, tvDistance, tvParkNum, tvTime, tvConfirm, tvCancel, tvTip, tvShareTime, tvNavigation;
    //    private long remain = 0;
    private MyAppointmentBean.AppointmentBean appointmentBean = null;
    private ParkInfo parkInfo;
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;
    private ConfirmCancelDialog confirmCancelDialog;
    private ConfirmCancelDialog confirmArriveDialog;
    private double lat;
    private double lng;

    @Override
    protected void inflaterLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.appointment_ing_layout, null, false);
    }

    @Override
    protected void onLazyLoad() {
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果
        mLocationOption.setOnceLocation(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        confirmCancelDialog = new ConfirmCancelDialog(getActivity(), "", "确认要取消预约吗？           \n\n注：取消后预约金不予退回", "确认", "取消");
        confirmCancelDialog.setClickListener(new ConfirmCancelDialog.ClickListener() {
            @Override
            public void confirm() {
                cancelAppointment();
                confirmCancelDialog.dismiss();
            }

            @Override
            public void cancel() {
                confirmCancelDialog.dismiss();
            }
        });
        confirmArriveDialog = new ConfirmCancelDialog(getActivity(), "", "确认到达目的地？", "确认", "取消");
        confirmArriveDialog.setClickListener(new ConfirmCancelDialog.ClickListener() {
            @Override
            public void confirm() {
                confirmAppointment();
                confirmArriveDialog.dismiss();
            }

            @Override
            public void cancel() {
                confirmArriveDialog.dismiss();
            }
        });
        requestPermissions();
        getMyAppointmentIng();
    }

    private void requestPermissions() {
        AndPermission.with(this)
                .permission(android.Manifest.permission.ACCESS_COARSE_LOCATION
                        , android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(permissions -> {
                    //启动定位
                    mLocationClient.startLocation();
                }).onDenied(permissions -> {
            if (!permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                mLocationClient.startLocation();
            }
            new PermissionSetting(this.getActivity()).showSetting(permissions);
        }).start();
    }

    private void getMyAppointmentIng() {
        getTime(Api.getDefaultService().getAppointment("1", "1", "1")
                , new RxObserver<BaseEntity<MyAppointmentBean>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseEntity<MyAppointmentBean> appointmentBean) {
                        MyAppointmentBean data = appointmentBean.getData();
                        if (data != null && data.getList() != null && data.getList().size() > 0) {
                            layout.findViewById(R.id.ll_content).setVisibility(View.VISIBLE);
                            MyAppointmentBean.AppointmentBean bean = data.getList().get(0);
                            parkInfo = bean.getPark();
                            updateView(bean);
                        } else {
                            noData();
                        }
                    }
                });
    }

    private void noData() {
        layout.findViewById(R.id.ll_content).setVisibility(View.GONE);
        tvTip.setVisibility(View.VISIBLE);
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
            tvTip.setVisibility(View.GONE);
            tvParkName.setText(parkInfo.getParking_name());
            tvAddress.setText(parkInfo.getArea_1() + " " + parkInfo.getArea_2() + " " + parkInfo.getArea_3() + " " + parkInfo.getArea_4() + " ");
            updateDistance();
        }
        tvParkNum.setText(appointmentBean.getUnit_id());
        tvTime.setText("(" + stampToDate(appointmentBean.getPay_time() + appointmentBean.getTimes()) + ")");
        if (appointmentBean.getUnit_id().endsWith("S")) {
            layout.findViewById(R.id.ll_share).setVisibility(View.VISIBLE);
//            tvShareTime.setText();等待接口返回数据
        } else {
            layout.findViewById(R.id.ll_share).setVisibility(View.GONE);
        }
        tvShareTime.setText((appointmentBean.getShare_time()));
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
        noData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                confirmArriveDialog.show();
                break;
            case R.id.tv_cancel:
                confirmCancelDialog.show();
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

    private void cancelAppointment() {
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

    private void updateDistance() {
        if (lat != 0 && lng != 0 && parkInfo != null) {
            LatLng latLng = new LatLng(lat, lng);
            LatLng latLng1 = new LatLng(Double.parseDouble(parkInfo.getLat()), Double.parseDouble(parkInfo.getLng()));
            float distance = AMapUtils.calculateLineDistance(latLng, latLng1) / 1000;
            DecimalFormat fNum = new DecimalFormat("##0.00");
            String dd = fNum.format(distance);
            tvDistance.setText(dd + "km");
        }
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

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        mLocationClient.stopLocation();
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                lat = aMapLocation.getLatitude();//获取纬度
                lng = aMapLocation.getLongitude();//获取经度
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
}
