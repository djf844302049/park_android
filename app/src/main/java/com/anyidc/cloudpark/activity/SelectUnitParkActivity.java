package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.andview.refreshview.utils.LogUtils;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.ItemClickListener;
import com.anyidc.cloudpark.adapter.ParkUnitNumAdapter;
import com.anyidc.cloudpark.adapter.ShareParkUnitAdapter;
import com.anyidc.cloudpark.dialog.SelectUnitParkDialog;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.ParkDetailBean;
import com.anyidc.cloudpark.moduel.ParkInfo;
import com.anyidc.cloudpark.moduel.ParkUnitInfoBean;
import com.anyidc.cloudpark.moduel.ShareParkUnitInfo;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.CacheData;
import com.anyidc.cloudpark.utils.ViewUtils;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/3.
 */

public class SelectUnitParkActivity extends BaseActivity implements View.OnClickListener,AMapLocationListener {
    private String id;

    private TextView tvTitle,tvAddress,tvDistance,tvTotal,tvRemain;
    private TextView tvFee;
    private TextView tvAppointment;
    private TextView tvIdAuth,tvCarAuth,tvPay;
    private RecyclerView recyclerView;
    private LinearLayout llImage;

    private ParkInfo parkInfo;
    private ArrayList<ParkUnitInfoBean> dataList;
    private ArrayList<ShareParkUnitInfo> shareList;

    private ParkUnitNumAdapter parkUnitNumAdapter;
    private ShareParkUnitAdapter shareParkUnitAdapter;

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    private double lat = 0;
    private double lng = 0;

    private SelectUnitParkDialog selectUnitParkDialog;

    public static void start(Context context,String id){
        Intent intent = new Intent(context,SelectUnitParkActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_unit_park;
    }

    @Override
    protected void initData() {
        initTitle("选择车位");
        tvTitle = findViewById(R.id.tv_parking_name);
        tvAddress = findViewById(R.id.tv_parking_address);
        tvDistance = findViewById(R.id.tv_parking_distance);
        tvTotal = findViewById(R.id.tv_unit_total_num);
        tvRemain = findViewById(R.id.tv_unit_remain);
        tvFee = findViewById(R.id.tv_unit_fee);
        tvAppointment = findViewById(R.id.tv_appointment);
        tvIdAuth = findViewById(R.id.tv_id_certification);
        tvCarAuth = findViewById(R.id.tv_car_certification);
        tvPay = findViewById(R.id.tv_recharge_deposit);
        recyclerView = findViewById(R.id.rv_unit_list);
        llImage = findViewById(R.id.ll_image);
        tvAppointment.setOnClickListener(this);
        tvIdAuth.setOnClickListener(this);
        tvCarAuth.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        getParkDetial();
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果
        mLocationOption.setOnceLocation(false);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_appointment:
                break;
            case R.id.tv_id_certification:
                break;
            case R.id.tv_car_certification:
                break;
            case R.id.tv_recharge_deposit:
                break;
        }
    }

    private void getParkDetial(){
        getTime(Api.getDefaultService().getParkDetail("1")
                , new RxObserver<BaseEntity<ParkDetailBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<ParkDetailBean> parkDetailBean) {
                        ParkDetailBean data = parkDetailBean.getData();
                        parkInfo = data.getPark();
                        dataList = data.getList();
                        shareList = data.getSharelist();
                        updateView();
                        updateDistance();
                    }
                });
    }

    /**
     * 初始化实景图
     */
    private void initRealImage(){
        llImage.removeAllViews();
        String imgStr = parkInfo.getThumb();
        String[] imgArr = imgStr.split(",");
        if(imgArr != null && imgArr.length > 0){
            for(String imgUrl : imgArr){
                View view = LayoutInflater.from(this).inflate(R.layout.single_image_layout,null,false);
                ImageView imageView = view.findViewById(R.id.iv_img);
                ViewGroup.LayoutParams lp = imageView.getLayoutParams();
                if(imgArr.length == 1){
                    lp.width = ViewUtils.getWindowWidth(this);
                }else{
                    lp.width = ViewUtils.getWindowWidth(this) - ViewUtils.dip2px(this,30);
                }
                lp.height = lp.width * 9/ 16;
                imageView.setLayoutParams(lp);
                Glide.with(this).load(imgUrl).dontAnimate().into(imageView);
                llImage.addView(view);
            }
        }
    }

    private void updateView(){
        if(parkInfo == null) return;
        tvTitle.setText(parkInfo.getParking_name());
        tvAddress.setText(parkInfo.getArea_1() + " " + parkInfo.getArea_2() + " " + parkInfo.getArea_3() + " " + parkInfo.getArea_4() + " " );
        tvTotal.setText(String.valueOf(parkInfo.getNum()));
        tvFee.setText("收费标准：首" + parkInfo.getFee().getFirst_time() + "小时"+parkInfo.getFee().getMoney()+"元,之后"+parkInfo.getFee().getHourly()+"元/小时");
        initRealImage();
        if(parkInfo.getType() == 0){
            llImage.setVisibility(View.GONE);
            if(shareList == null || shareList.size() <=0 ){
                recyclerView.setVisibility(View.GONE);
                return;
            }
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            shareParkUnitAdapter = new ShareParkUnitAdapter(this,shareList);
            shareParkUnitAdapter.setOnItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ShareParkUnitInfo shareParkUnitInfo = shareList.get(position);
                    if(shareParkUnitInfo != null && shareParkUnitInfo.getStatus() == 1 && shareParkUnitInfo.getFrozen_time() == 0){
                        showDialog(shareParkUnitInfo.getUnit_id(),"该车位为共享车位，可进行预约","","",String.valueOf(shareParkUnitInfo.getFee().getMoney()));
                        updateAppointBtn();
                    }else if(shareParkUnitInfo.getStatus() == 2){
                        showDialog(shareParkUnitInfo.getUnit_id(),"该车位已有车辆驶入","","",String.valueOf(shareParkUnitInfo.getFee().getMoney()));
                    }else{
                        showDialog(shareParkUnitInfo.getUnit_id(),"该车位为共享车位且已被预约","","",String.valueOf(shareParkUnitInfo.getFee().getMoney()));
                    }
                }
            });
            recyclerView.setAdapter(shareParkUnitAdapter);
        }else{
            llImage.setVisibility(View.VISIBLE);
            if(dataList == null || dataList.size() <=0 ){
                recyclerView.setVisibility(View.GONE);
                return;
            }
            recyclerView.setVisibility(View.VISIBLE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
            recyclerView.setLayoutManager(gridLayoutManager);
            parkUnitNumAdapter = new ParkUnitNumAdapter(this,dataList);
            parkUnitNumAdapter.setOnItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ParkUnitInfoBean unitInfoBean = dataList.get(position);
                    String feeStr = "首" + parkInfo.getFee().getFirst_time() + "小时"+parkInfo.getFee().getMoney()+"元,之后"+parkInfo.getFee().getHourly()+"元/小时";
                    if(unitInfoBean != null && unitInfoBean.getStatus() == 1 && unitInfoBean.getFrozen_time() == 0){
                        showDialog(unitInfoBean.getUnit_id(),"该车位可进行预约","","",feeStr);
                        updateAppointBtn();
                    }else if(unitInfoBean.getStatus() == 2){
                        showDialog(unitInfoBean.getUnit_id(),"该车位已有车辆驶入","","",feeStr);
                    }else{
                        showDialog(unitInfoBean.getUnit_id(),"该车位已被预约","","",feeStr);
                    }
                }
            });
            recyclerView.setAdapter(parkUnitNumAdapter);
        }
    }

    private void updateAppointBtn(){

    }

    private void showDialog(String unitNum,String des,String shareTime,String appointmentTime,String pFee){
        if(selectUnitParkDialog == null){
            selectUnitParkDialog = new SelectUnitParkDialog(SelectUnitParkActivity.this,unitNum,des,shareTime,appointmentTime,pFee);
        }else{
            selectUnitParkDialog.setText(unitNum,des,shareTime,appointmentTime,pFee);
        }
        selectUnitParkDialog.show();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                lat = aMapLocation.getLatitude();//获取纬度
                lng = aMapLocation.getLongitude();//获取经度
                updateDistance();
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    private void updateDistance(){
        if(lat != 0 && lng != 0 && parkInfo != null){
            LatLng latLng = new LatLng(lat,lng);
            LatLng latLng1 = new LatLng(Double.parseDouble(parkInfo.getLat()),Double.parseDouble(parkInfo.getLng()));
            float distance = AMapUtils.calculateLineDistance(latLng,latLng1);
            DecimalFormat  fnum  =  new DecimalFormat("##0.00");
            String dd =fnum.format(distance);
            tvDistance.setText(dd + "km");
        }
    }
}
