package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.IndexBean;
import com.anyidc.cloudpark.moduel.InfoBean;
import com.anyidc.cloudpark.moduel.InitBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.CacheData;
import com.anyidc.cloudpark.utils.LoginUtil;
import com.anyidc.cloudpark.wiget.VerticalTextView;
import com.bumptech.glide.Glide;
import com.yanzhenjie.permission.AndPermission;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, AMapLocationListener {
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;
    private VerticalTextView tvMess;
    private Banner banner;
    private List<String> imgs = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<String> bnUrls = new ArrayList<>();
    private ArrayList<String> mess = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        findViewById(R.id.tv_search_place).setOnClickListener(this);
        findViewById(R.id.iv_mine).setOnClickListener(this);
        tvMess = findViewById(R.id.tv_message);
        tvMess.setText(16.0f, 5, Color.parseColor("#959595"));
        tvMess.setAnimTime(300);
        tvMess.setTextStillTime(3000);
        tvMess.setOnItemClickListener(position -> Log.e("tag", "点击了" + position));
        banner = findViewById(R.id.bn_main);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        banner.setOnBannerListener(position -> Log.e("tag", "点击了" + position));
        mLocationClient = new AMapLocationClient(getApplicationContext());
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
        AndPermission.with(this)
                .permission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                .onGranted(permissions -> {
                    //启动定位
                    mLocationClient.startLocation();
                }).onDenied(permissions -> {
            //启动定位
            mLocationClient.startLocation();
        }).start();
        if (LoginUtil.isLogin()) {
            getUserData();
        }
        getInit();
        getData(0.155151515, 15.2565646564);
    }

    @Override
    protected void onStart() {
        super.onStart();
        banner.startAutoPlay();
        if (!tvMess.isScroll()) {
            tvMess.startAutoScroll();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.startAutoPlay();
        tvMess.stopAutoScroll();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int from = intent.getIntExtra("from", 0);
        if (from != 0) {
            getUserData();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_place:
                startActivity(new Intent(this, SearchMapActivity.class));
                break;
            case R.id.iv_mine:
                startActivity(new Intent(this, MineActivity.class));
                break;
        }
    }

    private void getInit() {
        getTime(Api.getDefaultService().appInit()
                , new RxObserver<BaseEntity<InitBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<InitBean> initBean) {

                    }
                });
    }

    private void getData(double lat, double lng) {
        getTime(Api.getDefaultService().getIndex(lat, lng)
                , new RxObserver<BaseEntity<IndexBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<IndexBean> indexBean) {
                        for (IndexBean.SlideBean slideBean : indexBean.getData().getSlide()) {
                            imgs.add(slideBean.getImage());
                            titles.add(slideBean.getTitle());
                            bnUrls.add(slideBean.getUrl());
                        }
                        banner.setBannerTitles(titles);
                        banner.setImages(imgs);
                        banner.start();
                        for (IndexBean.MessageBean messageBean : indexBean.getData().getMessage()) {
                            mess.add(messageBean.getMessage());
                        }
                        tvMess.setTextList(mess);
                        if (!tvMess.isScroll()) {
                            tvMess.startAutoScroll();
                        }
                    }
                });
    }

    private void getUserData() {
        getTime(Api.getDefaultService().getUserInfo(),
                new RxObserver<BaseEntity<InfoBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<InfoBean> infoBean) {
                        CacheData.setInfoBean(infoBean.getData());
                    }
                });
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        mLocationClient.stopLocation();
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                double latitude = aMapLocation.getLatitude();//获取纬度
                double longitude = aMapLocation.getLongitude();//获取经度
                getData(latitude, longitude);
                String city = aMapLocation.getCity();//城市信息
                Log.e("city", "---------->>" + city);
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
    }
}
