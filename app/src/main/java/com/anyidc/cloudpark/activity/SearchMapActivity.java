package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.AreaAdapter;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.HotAreaBean;
import com.anyidc.cloudpark.moduel.ParkSearchBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.SpUtils;
import com.anyidc.cloudpark.wiget.FlowLayoutManager;
import com.yanzhenjie.permission.AndPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/8.
 */

public class SearchMapActivity extends BaseActivity implements View.OnClickListener, AMap.OnMarkerClickListener, AMapLocationListener {

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;
    private SearchView searchView;
    private RecyclerView rlvHotArea;
    private RecyclerView rlvHistory;
    private List<String> hotAreaList = new ArrayList<>();
    private List<String> searchList = new ArrayList<>();
    private AreaAdapter hotAreaAdapter;
    private AreaAdapter searchAdapter;
    private MapView mapView;
    private LinearLayout llSearch;
    private LinearLayout llMap;
    private int page = 1;
    private int from;
    private AMap aMap;
    private double lat;
    private double lng;

    public static void actionStart(Context context, int from) {
        Intent intent = new Intent(context, SearchMapActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, int from, double lat, double lng) {
        Intent intent = new Intent(context, SearchMapActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_place;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.setOnMarkerClickListener(this);
        from = getIntent().getIntExtra("from", 0);
        switch (from) {
            case 1:
                break;
            case 2:
                AndPermission.with(this)
                        .permission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        .onGranted(permissions -> {
                            //启动定位
                            mLocationClient.startLocation();
                        }).onDenied(permissions -> {
                    //启动定位
                    mLocationClient.startLocation();
                }).start();
                llSearch.setVisibility(View.GONE);
                llMap.setVisibility(View.VISIBLE);
                break;
            default:
                getHotArea();
                break;
        }
    }

    @Override
    protected void initData() {
        searchView = findViewById(R.id.sv_map);
        initSearchView();
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_clear_history).setOnClickListener(this);
        llSearch = findViewById(R.id.ll_search);
        llMap = findViewById(R.id.ll_map_view);
        mapView = findViewById(R.id.map_view);
        rlvHotArea = findViewById(R.id.rlv_hot_area);
        RecyclerView.LayoutManager layoutManager = new FlowLayoutManager();
        rlvHotArea.setLayoutManager(layoutManager);
        rlvHotArea.setNestedScrollingEnabled(false);
        hotAreaAdapter = new AreaAdapter(hotAreaList);
        rlvHotArea.setAdapter(hotAreaAdapter);
        hotAreaAdapter.setOnItemClickListener((view, position) -> search(hotAreaList.get(position)));
        hotAreaAdapter.notifyDataSetChanged();
        rlvHistory = findViewById(R.id.rlv_search_history);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rlvHistory.setLayoutManager(manager);
        List<String> historyList = SpUtils.getObject(SpUtils.SEARCHLIST, List.class);
        if (historyList != null) {
            searchList.addAll(historyList);
        }
        searchAdapter = new AreaAdapter(searchList);
        rlvHistory.setAdapter(searchAdapter);
        searchAdapter.setOnItemClickListener((view, position) -> search(searchList.get(position)));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:

                break;
            case R.id.tv_clear_history:
                searchList.clear();
                searchAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initSearchView() {
        // 为该SearchView组件设置事件监听器
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                search(query);
                if (!searchList.contains(query)) {
                    searchList.add(query);
                    searchAdapter.notifyDataSetChanged();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        // 设置该SearchView内默认显示的提示文本
        searchView.setQueryHint(getResources().getString(R.string.search_place));
        searchView.clearFocus();

        int searchPlateId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate != null) {
            int searchTextId = searchPlate.getContext().getResources()
                    .getIdentifier("android:id/search_src_text", null, null);
            //文字颜色
            TextView searchText = searchPlate.findViewById(searchTextId);
            if (searchText != null) {
                searchText.setTextSize(14f);
                searchText.setTextColor(ContextCompat.getColor(this, R.color.text_color_black));
                searchText.setHintTextColor(ContextCompat.getColor(this, R.color.text_color_gray));
            }

            //光标颜色
//            try {
//                Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
//                mCursorDrawableRes.setAccessible(true);
//                mCursorDrawableRes.set(searchText, R.drawable.cursor_color);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    private void getHotArea() {
        getTime(Api.getDefaultService().getHotSearch()
                , new RxObserver<BaseEntity<List<HotAreaBean>>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<List<HotAreaBean>> hotAreaBean) {
                        List<HotAreaBean> data = hotAreaBean.getData();
                        for (HotAreaBean datum : data) {
                            hotAreaList.add(datum.getSearch_name());
                        }
                        hotAreaAdapter.notifyDataSetChanged();
                        searchView.clearFocus();
                    }
                });
    }

    private void getNearby() {
        getTime(Api.getDefaultService().searchParkNearby(1, 10, lat, lng),
                new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        searchView.clearFocus();
                    }
                });
    }

    private void search(String target) {
        getTime(Api.getDefaultService().parkingSearch(10, page, target)
                , new RxObserver<BaseEntity<ParkSearchBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<ParkSearchBean> parkSearchBean) {
                        ParkSearchBean data = parkSearchBean.getData();
                        llSearch.setVisibility(View.GONE);
                        llMap.setVisibility(View.VISIBLE);
                        searchView.clearFocus();
                        LatLng latLng = new LatLng(data.getLngLat().getLat(), data.getLngLat().getLng());
                        //设置中心点和缩放比例
                        CameraUpdate cameraUpdate = CameraUpdateFactory
                                .newCameraPosition(new CameraPosition(latLng, 18, 0, 30));
                        aMap.moveCamera(cameraUpdate);
                        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                        aMap.addMarker(new MarkerOptions().position(latLng).snippet(target));
                        List<ParkSearchBean.ParkBean> park = data.getPark();
                        for (ParkSearchBean.ParkBean parkBean : park) {
                            latLng = new LatLng(parkBean.getLat(), parkBean.getLng());
                            MarkerOptions markerOption = new MarkerOptions();
                            markerOption.position(latLng);
                            markerOption.draggable(false);//设置Marker可拖动
                            markerOption.snippet(parkBean.getParking_name());
//                            markerOption.icon(BitmapDescriptorFactory.fromView(view));
                            aMap.addMarker(markerOption);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        SpUtils.setObject(SpUtils.SEARCHLIST, searchList);
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        mLocationClient.stopLocation();
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                lat = aMapLocation.getLatitude();//获取纬度
                lng = aMapLocation.getLongitude();//获取经度
                LatLng latLng = new LatLng(lat, lng);
                //设置中心点和缩放比例
                CameraUpdate cameraUpdate = CameraUpdateFactory
                        .newCameraPosition(new CameraPosition(latLng, 18, 0, 30));
                aMap.moveCamera(cameraUpdate);
                aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                aMap.addMarker(new MarkerOptions().position(latLng).snippet("我的位置"));
                getNearby();
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
}
