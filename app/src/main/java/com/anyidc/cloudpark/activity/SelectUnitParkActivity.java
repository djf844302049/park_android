package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.ParkDetailBean;
import com.anyidc.cloudpark.moduel.ParkInfo;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

/**
 * Created by Administrator on 2018/4/3.
 */

public class SelectUnitParkActivity extends BaseActivity implements View.OnClickListener{
    private String id;

    private TextView tvTitle,tvAddress,tvDistance,tvTotal,tvRemain;
    private TextView tvFee;
    private TextView tvAppointment;
    private TextView tvIdAuth,tvCarAuth,tvPay;

    private ParkInfo parkInfo;
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
                        updateView();
                    }
                });
    }

    private void updateView(){
        if(parkInfo == null) return;
        tvTitle.setText(parkInfo.getParking_name());
        tvAddress.setText(parkInfo.getArea_1() + " " + parkInfo.getArea_2() + " " + parkInfo.getArea_3() + " " + parkInfo.getArea_4() + " " );
        tvTotal.setText(String.valueOf(parkInfo.getNum()));
        tvFee.setText("收费标准：首" + parkInfo.getFee().getFirst_time() + "小时"+parkInfo.getFee().getMoney()+"元,之后"+parkInfo.getFee().getHourly()+"元/小时");
    }
}
