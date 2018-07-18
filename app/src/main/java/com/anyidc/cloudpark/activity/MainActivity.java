package com.anyidc.cloudpark.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.anyidc.cloudpark.BaseApplication;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.IndexBean;
import com.anyidc.cloudpark.moduel.InitBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.PermissionSetting;
import com.anyidc.cloudpark.utils.ToastUtil;
import com.anyidc.cloudpark.wiget.VerticalTextView;
import com.bumptech.glide.Glide;
import com.yanzhenjie.permission.AndPermission;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements AMapLocationListener {
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;
    private VerticalTextView tvMess;
    private Banner banner;
    private TextView tvCity;
    private List<String> imgs = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<String> bnUrls = new ArrayList<>();
    private ArrayList<String> mess = new ArrayList<>();
    private long mExitTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        findViewById(R.id.tv_search_place).setOnClickListener(clickListener);
        findViewById(R.id.iv_mine).setOnClickListener(clickListener);
        findViewById(R.id.iv_pay_park).setOnClickListener(clickListener);
        findViewById(R.id.iv_search_park).setOnClickListener(clickListener);
        findViewById(R.id.iv_park_order).setOnClickListener(clickListener);
        tvMess = findViewById(R.id.tv_message);
        tvMess.setText(16.0f, 5, Color.parseColor("#959595"));
        tvMess.setAnimTime(300);
        tvMess.setTextStillTime(3000);
        tvMess.setOnItemClickListener(position -> Log.e("tag", "点击了" + position));
        tvCity = findViewById(R.id.tv_city);
        banner = findViewById(R.id.bn_main);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        banner.setOnBannerListener(position -> WebViewActivity.actionStart(MainActivity.this, bnUrls.get(position), 0));
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
                .permission(Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.CAMERA
                        , android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(permissions -> {
                    //启动定位
                    mLocationClient.startLocation();
                }).onDenied(permissions -> {
            if (permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                getData(0d, 0d);
            } else {
                mLocationClient.startLocation();
            }
            new PermissionSetting(MainActivity.this).showSetting(permissions);
        }).start();
        getInit();
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
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_place:
                SearchMapActivity.actionStart(this, 0);
                break;
            case R.id.iv_mine:
                startActivity(new Intent(this, MineActivity.class));
                break;
            case R.id.iv_pay_park:
                startActivity(new Intent(this, PayParkActivity.class));
                break;
            case R.id.iv_search_park:
                SearchMapActivity.actionStart(this, 2);
                break;
            case R.id.iv_park_order:
                SearchMapActivity.actionStart(this, 1);
                break;
        }
    }

    private void getInit() {
        getTime(Api.getDefaultService().appInit()
                , new RxObserver<BaseEntity<InitBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<InitBean> initBean) {
                        InitBean data = initBean.getData();
                        switch (data.getIs_update()) {
                            case 1:
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("提示")
                                        .setMessage("云能智能停车有新版本啦，是否前往更新？")
                                        .setPositiveButton("确定", (dialog, which) -> {
                                            if (TextUtils.isEmpty(data.getDownload_url()))
                                                openApplicationMarket();
                                            else {
                                                openLinkBySystem(data.getDownload_url());
                                            }
                                        })
                                        .setNegativeButton("取消", null).show();
                                break;
                            case 2:
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("提示")
                                        .setMessage("云能智能停车有重大版本更新，请前往更新后再继续使用")
                                        .setPositiveButton("确定", (dialog, which) -> {
                                            if (TextUtils.isEmpty(data.getDownload_url()))
                                                openApplicationMarket();
                                            else {
                                                openLinkBySystem(data.getDownload_url());
                                            }
                                        })
                                        .show();
                                break;
                        }
                    }
                });
    }

    private void openApplicationMarket() {
        try {
            String str = "market://details?id=" + getPackageName();
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setData(Uri.parse(str));
            startActivity(localIntent);
        } catch (Exception e) {
            // 打开应用商店失败 可能是没有手机没有安装应用市场
            e.printStackTrace();
        }
    }

    private void openLinkBySystem(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
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

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        mLocationClient.stopLocation();
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                double latitude = aMapLocation.getLatitude();//获取纬度
                double longitude = aMapLocation.getLongitude();//获取经度
                getData(latitude, longitude);
                String city = aMapLocation.getCity();//城市信息
                tvCity.setText(city);
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                ToastUtil.showToast("定位失败，请检查定位权限是否开启", Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtil.showToast("再按一次退出程序", Toast.LENGTH_SHORT);
            mExitTime = System.currentTimeMillis();
        } else {
            BaseApplication.getInstance().exitApp();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.unRegisterLocationListener(this);
        mLocationClient.onDestroy();
    }
}
