package com.anyidc.cloudpark.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.andview.refreshview.XRefreshView;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.AreaAdapter;
import com.anyidc.cloudpark.adapter.CityAreaAdapter;
import com.anyidc.cloudpark.adapter.ParkListAdapter;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.CityAreaBean;
import com.anyidc.cloudpark.moduel.HotAreaBean;
import com.anyidc.cloudpark.moduel.ParkSearchBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.LoginUtil;
import com.anyidc.cloudpark.utils.PermissionSetting;
import com.anyidc.cloudpark.utils.SpUtils;
import com.anyidc.cloudpark.utils.ToastUtil;
import com.anyidc.cloudpark.utils.ViewUtils;
import com.anyidc.cloudpark.wiget.FlowLayoutManager;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/8.
 */

public class SearchMapActivity extends BaseActivity implements AMap.OnMarkerClickListener, AMapLocationListener {

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;
    private SearchView searchView;
    private RecyclerView rlvHotArea;
    private RecyclerView rlvHistory;
    private RecyclerView rlvPark;
    private XRefreshView refreshView;
    private RelativeLayout rlParkDetail;
    private List<String> hotAreaList = new ArrayList<>();
    private List<String> searchList = new ArrayList<>();
    private List<ParkSearchBean.ParkBean> parkList = new ArrayList<>();
    private List<ParkSearchBean.ParkBean> nearbyList = new ArrayList<>();
    private List<ParkSearchBean.ParkBean> searchParkList = new ArrayList<>();
    private List<CityAreaBean> areaBeans = new ArrayList<>();
    private PopupWindow areaWindow;
    private AreaAdapter hotAreaAdapter;
    private AreaAdapter searchAdapter;
    private ParkListAdapter parkListAdapter;
    private CityAreaAdapter cityAreaAdapter;
    private MapView mapView;
    private LinearLayout llSearch, llParkMess, llMap, llParkList;
    private TextView tvCancel, tvArea, tvNearby, tvParkName, tvParkAddress, tvParkTotalNum, tvParkRemainNum, tvParkFeeRegu, tvDistance;
    private ImageView ivParkList, ivArrow;
    private ParkSearchBean.ParkBean parkBean;
    private int areaId;
    private int page = 1;
    private int nearPage = 1;
    private int areaPage = 1;
    private boolean nearbyLoad = true;
    private boolean searchLoad = true;
    private boolean search;
    private boolean nearby;
    private boolean area;
    private String target;
    private String city;
    private String dis;
    private String targetPlace;
    private double targetLat;
    private double targetLng;
    private double searchLat;
    private double searchLng;
    private int from;
    private AMap aMap;
    private double lat;
    private double lng;

    public static void actionStart(Context context, int from) {
        Intent intent = new Intent(context, SearchMapActivity.class);
        intent.putExtra("from", from);
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
                llSearch.setVisibility(View.GONE);
                llMap.setVisibility(View.GONE);
                llParkList.setVisibility(View.VISIBLE);
                break;
            case 2:
                llSearch.setVisibility(View.GONE);
                llParkList.setVisibility(View.GONE);
                llMap.setVisibility(View.VISIBLE);
                llParkMess.setVisibility(View.GONE);
                break;
            default:
                llSearch.setVisibility(View.VISIBLE);
                break;
        }
        requestPermissions();
        getHotArea();
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
            new PermissionSetting(SearchMapActivity.this).showSetting(permissions);
        }).start();
    }

    @Override
    protected void initData() {
        searchView = findViewById(R.id.sv_map);
        initSearchView();
        tvCancel = findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(clickListener);
        ivParkList = findViewById(R.id.iv_park_list);
        ivParkList.setOnClickListener(clickListener);
        findViewById(R.id.iv_back_search).setOnClickListener(clickListener);
        findViewById(R.id.iv_back_map).setOnClickListener(clickListener);
        findViewById(R.id.iv_back_list).setOnClickListener(clickListener);
        findViewById(R.id.tv_clear_history).setOnClickListener(clickListener);
        findViewById(R.id.tv_search_map).setOnClickListener(clickListener);
        findViewById(R.id.tv_search_list).setOnClickListener(clickListener);
        findViewById(R.id.tv_list_cancel).setOnClickListener(clickListener);
        rlParkDetail = findViewById(R.id.rl_park_detail);
        rlParkDetail.setOnClickListener(clickListener);
        rlParkDetail.setEnabled(false);
        llSearch = findViewById(R.id.ll_search);
        llMap = findViewById(R.id.ll_map_view);
        llParkList = findViewById(R.id.ll_park_list);
        llParkMess = findViewById(R.id.ll_park_message);
        tvParkName = findViewById(R.id.tv_place);
        tvParkAddress = findViewById(R.id.tv_address);
        tvDistance = findViewById(R.id.tv_distance);
        tvParkTotalNum = findViewById(R.id.tv_park_total_num);
        tvParkRemainNum = findViewById(R.id.tv_park_remain_num);
        tvParkFeeRegu = findViewById(R.id.tv_park_fee_regular);
        ivArrow = findViewById(R.id.iv_arrow);
        findViewById(R.id.btn_navigation).setOnClickListener(clickListener);
        tvArea = findViewById(R.id.tv_area_position);
        tvArea.setOnClickListener(clickListener);
        tvNearby = findViewById(R.id.tv_distance_first);
        tvNearby.setOnClickListener(clickListener);
        mapView = findViewById(R.id.map_view);
        rlvHotArea = findViewById(R.id.rlv_hot_area);
        RecyclerView.LayoutManager layoutManager = new FlowLayoutManager();
        rlvHotArea.setLayoutManager(layoutManager);
        rlvHotArea.setNestedScrollingEnabled(false);
        hotAreaAdapter = new AreaAdapter(hotAreaList);
        rlvHotArea.setAdapter(hotAreaAdapter);
        hotAreaAdapter.setOnItemClickListener((view, position) -> {
            page = 1;
            target = hotAreaList.get(position);
            search(target);
        });
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
        searchAdapter.setOnItemClickListener((view, position) -> {
            page = 1;
            target = searchList.get(position);
            search(target);
        });
        rlvPark = findViewById(R.id.rlv_park_list);
        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(this);
        rlvPark.setLayoutManager(manager1);
        parkListAdapter = new ParkListAdapter(parkList);
        parkListAdapter.setOnItemClickListener((view, position) -> {
            ParkSearchBean.ParkBean parkBean = parkList.get(position);
            if (!LoginUtil.isLogin()) {
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }
            SelectUnitParkActivity.start(SearchMapActivity.this, String.valueOf(parkBean.getParking_id()), parkBean.getType());
        });
        rlvPark.setAdapter(parkListAdapter);
        refreshView = findViewById(R.id.my_xrefreshview);
        refreshView.setPullRefreshEnable(false);
        refreshView.setPullLoadEnable(true);
        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                if (search) {
                    search(target);
                }
                if (nearby) {
                    getNearby();
                }
                if (area) {
                    getAreaParkList();
                }
            }
        });
    }

    @Override
    public void onCheckDoubleClick(View view) {
        super.onCheckDoubleClick(view);
        switch (view.getId()) {
            case R.id.tv_cancel:
                if (from == 0) {
                    finish();
                } else {
                    searchView.clearFocus();
                    llSearch.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_clear_history:
                searchList.clear();
                searchAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_back_search:
            case R.id.iv_back_map:
            case R.id.iv_back_list:
                finish();
                break;
            case R.id.iv_park_list:
                llParkList.setVisibility(View.VISIBLE);
                if (search) {
                    refreshView.setPullLoadEnable(searchLoad);
                }
                if (nearby) {
                    refreshView.setPullLoadEnable(nearbyLoad);
                }
                break;
            case R.id.tv_search_map:
            case R.id.tv_search_list:
                llSearch.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_area_position:
                getAreaList();
                break;
            case R.id.tv_distance_first:
                if (nearbyList.size() == 0) {
                    getNearby();
                } else {
                    parkList.clear();
                    parkList.addAll(nearbyList);
                    parkListAdapter.notifyDataChanged();
                }
                break;
            case R.id.btn_navigation:
                jumpToMap();
                break;
            case R.id.rl_park_detail:
                if (parkBean == null) {
                    return;
                }
                if (!LoginUtil.isLogin()) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                SelectUnitParkActivity.start(this, String.valueOf(parkBean.getParking_id()), parkBean.getType());
                break;
            case R.id.tv_list_cancel:
                if (from == 1) {
                    finish();
                    return;
                }
                llParkList.setVisibility(View.GONE);
                break;
        }
    }

    private void initSearchView() {
        // 设置该SearchView内默认显示的提示文本
        searchView.setQueryHint(getResources().getString(R.string.search_place));
        searchView.clearFocus();
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = searchView.findViewById(id);
        if (searchText != null) {
            searchText.setTextSize(14f);
            searchText.setTextColor(ContextCompat.getColor(this, R.color.text_color_black));
            searchText.setHintTextColor(ContextCompat.getColor(this, R.color.text_color_gray));
        }
        int editId = searchView.getContext().getResources().getIdentifier("android:id/search_edit_frame", null, null);
        LinearLayout ll = searchView.findViewById(editId);
        ll.setGravity(Gravity.CENTER_VERTICAL);
        ll.setBackground(null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 0);
        lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 33, getResources().getDisplayMetrics());
        ll.setLayoutParams(lp);
        // 为该SearchView组件设置事件监听器
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                page = 1;
                target = query;
                search(target);
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
        nearby = true;
        search = false;
        area = false;
        getTime(Api.getDefaultService().searchParkNearby(nearPage, 10, lat, lng),
                new RxObserver<BaseEntity<ParkSearchBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<ParkSearchBean> baseEntity) {
                        refreshView.stopLoadMore();
                        searchView.clearFocus();
                        nearPage++;
                        List<ParkSearchBean.ParkBean> park = baseEntity.getData().getPark();
                        if (park.size() < 10) {
                            nearbyLoad = false;
                            refreshView.setPullLoadEnable(false);
                        }
                        for (ParkSearchBean.ParkBean parkBean : park) {
                            LatLng latLng = new LatLng(parkBean.getLat(), parkBean.getLng());
                            MarkerOptions markerOption = new MarkerOptions();
                            markerOption.position(latLng);
                            markerOption.draggable(false);//设置Marker可拖动
                            markerOption.snippet(parkBean.getParking_name());
                            View inflate = LayoutInflater.from(SearchMapActivity.this).inflate(R.layout.layout_map_mark, null);
                            TextView tvMark = inflate.findViewById(R.id.tv_mark);
                            switch (parkBean.getType()) {
                                case 0://附近停车场
                                    tvMark.setBackgroundResource(R.mipmap.img_other_park);
                                    break;
                                case 1://云能停车场
                                    tvMark.setBackgroundResource(R.mipmap.img_yunneng_park);
                                    tvMark.setTextColor(ContextCompat.getColor(SearchMapActivity.this, R.color.bg_blue));
                                    tvMark.setText(parkBean.getAvailable_num() > 99 ? "99+" : String.valueOf(parkBean.getAvailable_num()));
                                    break;
                                case 2://共享停车场
                                    tvMark.setBackgroundResource(R.mipmap.img_share_logo);
                                    tvMark.setTextColor(ContextCompat.getColor(SearchMapActivity.this, R.color.white));
                                    tvMark.setText(parkBean.getAvailable_num() > 99 ? "99+" : String.valueOf(parkBean.getAvailable_num()));
                                    break;
                            }
                            markerOption.icon(BitmapDescriptorFactory.fromView(inflate));
                            Marker marker = aMap.addMarker(markerOption);
                            marker.setObject(parkBean);
                        }
                        nearbyList.addAll(park);
                        parkList.clear();
                        parkList.addAll(nearbyList);
                        parkListAdapter.notifyDataChanged();
                    }
                });
    }

    private void search(String target) {
        from = 4;
        this.target = target;
        nearby = false;
        search = true;
        area = false;
        getTime(Api.getDefaultService().parkingSearch(10, page, target)
                , new RxObserver<BaseEntity<ParkSearchBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<ParkSearchBean> parkSearchBean) {
                        refreshView.stopLoadMore();
                        ParkSearchBean data = parkSearchBean.getData();
                        searchParkList.addAll(data.getPark());
                        llSearch.setVisibility(View.GONE);
                        llParkList.setVisibility(View.GONE);
                        llMap.setVisibility(View.VISIBLE);
                        tvParkTotalNum.setVisibility(View.GONE);
                        tvParkRemainNum.setVisibility(View.GONE);
                        tvParkFeeRegu.setVisibility(View.GONE);
                        ivArrow.setVisibility(View.GONE);
                        llParkMess.setVisibility(View.VISIBLE);
                        searchView.clearFocus();
                        if (page == 1) {
                            aMap.clear();
                            searchLoad = true;
                            searchParkList.clear();
                            searchLat = data.getLngLat().getLat();
                            searchLng = data.getLngLat().getLng();
                            targetPlace = target;
                            targetLat = searchLat;
                            targetLng = searchLng;
                            LatLng latLng = new LatLng(searchLat, searchLng);
                            //设置中心点和缩放比例
                            CameraUpdate cameraUpdate = CameraUpdateFactory
                                    .newCameraPosition(new CameraPosition(latLng, 18, 0, 30));
                            aMap.moveCamera(cameraUpdate);
                            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                            aMap.addMarker(new MarkerOptions().position(latLng).snippet(target));
                            tvParkName.setText(target);
                            float distance = AMapUtils.calculateLineDistance(latLng, new LatLng(lat, lng)) / 1000;
                            DecimalFormat format = new DecimalFormat("0.00");
                            dis = format.format(distance) + "km";
                            tvDistance.setText("距离：" + dis);
                            tvParkAddress.setText(target);
                        }
                        page++;
                        List<ParkSearchBean.ParkBean> park = data.getPark();
                        if (park.size() < 10) {
                            searchLoad = false;
                        }
                        for (ParkSearchBean.ParkBean parkBean : park) {
                            LatLng latLng = new LatLng(parkBean.getLat(), parkBean.getLng());
                            MarkerOptions markerOption = new MarkerOptions();
                            markerOption.position(latLng);
                            markerOption.draggable(false);//设置Marker可拖动
                            markerOption.snippet(parkBean.getParking_name());
                            View inflate = LayoutInflater.from(SearchMapActivity.this).inflate(R.layout.layout_map_mark, null);
                            TextView tvMark = inflate.findViewById(R.id.tv_mark);
                            switch (parkBean.getType()) {
                                case 0://附近停车场
                                    tvMark.setBackgroundResource(R.mipmap.img_other_park);
                                    break;
                                case 1://云能停车场
                                    tvMark.setBackgroundResource(R.mipmap.img_yunneng_park);
                                    tvMark.setTextColor(ContextCompat.getColor(SearchMapActivity.this, R.color.bg_blue));
                                    tvMark.setText(parkBean.getAvailable_num() > 99 ? "99+" : String.valueOf(parkBean.getAvailable_num()));
                                    break;
                                case 2://共享停车场
                                    tvMark.setBackgroundResource(R.mipmap.img_share_logo);
                                    tvMark.setTextColor(ContextCompat.getColor(SearchMapActivity.this, R.color.white));
                                    tvMark.setText(parkBean.getAvailable_num() > 99 ? "99+" : String.valueOf(parkBean.getAvailable_num()));
                                    break;
                            }
                            markerOption.icon(BitmapDescriptorFactory.fromView(inflate));
                            Marker marker = aMap.addMarker(markerOption);
                            marker.setObject(parkBean);
                        }
                        parkList.clear();
                        parkList.addAll(park);
                        parkListAdapter.notifyDataChanged();
                    }
                });
    }

    private void getAreaList() {
        if (areaBeans.size() == 0) {
            getTime(Api.getDefaultService().getAreaInfo(city),
                    new RxObserver<BaseEntity<List<CityAreaBean>>>(this, true) {
                        @Override
                        public void onSuccess(BaseEntity<List<CityAreaBean>> baseEntity) {
                            areaBeans.addAll(baseEntity.getData());
                            showAreaWindow();
                        }
                    });
        } else {
            showAreaWindow();
        }
    }

    private void getAreaParkList() {
        area = true;
        search = false;
        nearby = false;
        getTime(Api.getDefaultService().searchParkById(areaPage, 10, areaId)
                , new RxObserver<BaseEntity<ParkSearchBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<ParkSearchBean> baseEntity) {
                        if (areaPage == 1) {
                            parkList.clear();
                        }
                        refreshView.stopLoadMore();
                        areaPage++;
                        ParkSearchBean data = baseEntity.getData();
                        if (data.getPark().size() < 10) {
                            refreshView.setPullLoadEnable(false);
                        }
                        parkList.addAll(data.getPark());
                        parkListAdapter.notifyDataChanged();
                    }
                });
    }

    private void showAreaWindow() {
        if (areaWindow == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_popuwindow_area, null);
            RecyclerView rlvArea = view.findViewById(R.id.rlv_area);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
            rlvArea.setLayoutManager(manager);
            cityAreaAdapter = new CityAreaAdapter(areaBeans);
            cityAreaAdapter.setOnItemClickListener((view1, position) -> {
                areaPage = 1;
                areaId = areaBeans.get(position).getId();
                areaWindow.dismiss();
                getAreaParkList();
            });
            rlvArea.setAdapter(cityAreaAdapter);
            areaWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            areaWindow.setOutsideTouchable(true);
            areaWindow.setBackgroundDrawable(new BitmapDrawable());
        }
        areaWindow.showAsDropDown(tvArea, ViewUtils.dip2px(this, -29), ViewUtils.dip2px(this, 7));
    }

    @Override
    protected void onDestroy() {
        SpUtils.setObject(SpUtils.SEARCHLIST, searchList);
        mapView.onDestroy();
        hotAreaList.clear();
        parkList.clear();
        nearbyList.clear();
        searchParkList.clear();
        areaBeans.clear();
        super.onDestroy();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getObject() instanceof ParkSearchBean.ParkBean) {
            parkBean = (ParkSearchBean.ParkBean) marker.getObject();
            llParkMess.setVisibility(View.VISIBLE);
            switch (parkBean.getType()) {
                case 0:
                    tvParkTotalNum.setVisibility(View.GONE);
                    tvParkRemainNum.setVisibility(View.GONE);
                    tvParkFeeRegu.setVisibility(View.GONE);
                    ivArrow.setVisibility(View.GONE);
                    tvParkName.setText(parkBean.getParking_name());
                    tvDistance.setText("距离：" + parkBean.getDistance() + "km");
                    tvParkAddress.setText(parkBean.getAddress());
                    rlParkDetail.setEnabled(false);
                    break;
                default:
                    tvParkTotalNum.setVisibility(View.VISIBLE);
                    tvParkRemainNum.setVisibility(View.VISIBLE);
                    tvParkFeeRegu.setVisibility(View.VISIBLE);
                    ivArrow.setVisibility(View.VISIBLE);
                    tvParkName.setText(parkBean.getParking_name());
                    tvDistance.setText("距离：" + parkBean.getDistance() + "km");
                    tvParkAddress.setText(parkBean.getAddress());
                    tvParkTotalNum.setText("车位数：" + parkBean.getNum());
                    tvParkRemainNum.setText("空车位数：" + parkBean.getAvailable_num());
                    StringBuffer fee = new StringBuffer("收费标准：");
                    for (String s : parkBean.getFee_desc()) {
                        fee.append(s).append("，");
                    }
                    fee.deleteCharAt(fee.lastIndexOf("，"));
                    fee.append("。");
                    tvParkFeeRegu.setText(fee);
                    rlParkDetail.setEnabled(true);
                    break;
            }
            targetPlace = marker.getSnippet();
            targetLat = parkBean.getLat();
            targetLng = parkBean.getLng();
        } else {
            if ("我的位置".equals(marker.getSnippet())) {
                llParkMess.setVisibility(View.GONE);
                return false;
            }
            tvParkTotalNum.setVisibility(View.GONE);
            tvParkRemainNum.setVisibility(View.GONE);
            tvParkFeeRegu.setVisibility(View.GONE);
            ivArrow.setVisibility(View.GONE);
            tvParkName.setText(target);
            tvDistance.setText("距离：" + dis);
            tvParkAddress.setText(target);
            rlParkDetail.setEnabled(false);
            targetPlace = target;
            targetLat = searchLat;
            targetLng = searchLng;
        }
        return false;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        mLocationClient.stopLocation();
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                lat = aMapLocation.getLatitude();//获取纬度
                lng = aMapLocation.getLongitude();//获取经度
                SpUtils.set(SpUtils.MYLATITUDE, String.valueOf(lat));
                SpUtils.set(SpUtils.MYLONGITUDE, String.valueOf(lng));
                city = aMapLocation.getCity();
                if (from != 0) {
                    getNearby();
                    LatLng latLng = new LatLng(lat, lng);
                    //设置中心点和缩放比例
                    CameraUpdate cameraUpdate = CameraUpdateFactory
                            .newCameraPosition(new CameraPosition(latLng, 18, 0, 30));
                    aMap.moveCamera(cameraUpdate);
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                    aMap.addMarker(new MarkerOptions().position(latLng).snippet("我的位置"));
                }
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    private void jumpToMap() {
        try {
            Intent intent = Intent.getIntent("androidamap://viewMap?sourceApplication=我的位置&poiname=" + targetPlace +
                    "&lat=" + targetLat + "&lon=" + targetLng);
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
