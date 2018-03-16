package com.anyidc.cloudpark.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/3/16.
 */

public class ParkChargeActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvChargeNum, tvParkDuration, tvSharePark, tvDiscount;
    private ImageView ivHelp, ivBalancePay, ivAlPay, ivWxPay;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_park;
    }

    @Override
    protected void initData() {
        initTitle("停车付费");
        tvChargeNum = findViewById(R.id.tv_park_charge_num);
        tvParkDuration = findViewById(R.id.tv_park_duration);
        tvSharePark = findViewById(R.id.tv_share_park);
        tvDiscount = findViewById(R.id.tv_discount);
        ivHelp = findViewById(R.id.iv_help);
        ivAlPay = findViewById(R.id.iv_al_pay);
        ivWxPay = findViewById(R.id.iv_wx_pay);
        ivBalancePay = findViewById(R.id.iv_balance_pay);
        findViewById(R.id.btn_pay).setOnClickListener(this);
        findViewById(R.id.ll_balance_pay).setOnClickListener(this);
        findViewById(R.id.ll_al_pay).setOnClickListener(this);
        findViewById(R.id.ll_wx_pay).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_balance_pay:
                ivBalancePay.setVisibility(View.VISIBLE);
                ivAlPay.setVisibility(View.GONE);
                ivWxPay.setVisibility(View.GONE);
                break;
            case R.id.ll_al_pay:
                ivBalancePay.setVisibility(View.GONE);
                ivAlPay.setVisibility(View.VISIBLE);
                ivWxPay.setVisibility(View.GONE);
                break;
            case R.id.ll_wx_pay:
                ivBalancePay.setVisibility(View.GONE);
                ivAlPay.setVisibility(View.GONE);
                ivWxPay.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_pay:
                break;
        }
    }
}
