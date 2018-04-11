package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.ItemClickListener;
import com.anyidc.cloudpark.adapter.ParkUnitNumAdapter;
import com.anyidc.cloudpark.adapter.ShareParkUnitAdapter;
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

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/3.
 */

public class SelectUnitParkActivity extends BaseActivity implements View.OnClickListener{
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

                }
            });
            recyclerView.setAdapter(parkUnitNumAdapter);
        }

    }
}
