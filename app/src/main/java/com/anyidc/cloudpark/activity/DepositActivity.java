package com.anyidc.cloudpark.activity;

import android.view.View;
import android.widget.ImageView;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/3/15.
 */

public class DepositActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivAlPay, ivWxPay;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_deposit;
    }

    @Override
    protected void initData() {
        initTitle("充值余额");
        findViewById(R.id.ll_al_pay).setOnClickListener(this);
        findViewById(R.id.ll_wx_pay).setOnClickListener(this);
        ivAlPay = findViewById(R.id.iv_al_pay);
        ivWxPay = findViewById(R.id.iv_wx_pay);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_al_pay:
                ivAlPay.setVisibility(View.VISIBLE);
                ivWxPay.setVisibility(View.GONE);
                break;
            case R.id.ll_wx_pay:
                ivAlPay.setVisibility(View.GONE);
                ivWxPay.setVisibility(View.VISIBLE);
                break;
        }
    }
}
