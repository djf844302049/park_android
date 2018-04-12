package com.anyidc.cloudpark.activity;

import android.view.View;
import android.widget.ImageView;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/3/15.
 */

public class DepositActivity extends BaseActivity {

    private ImageView ivAlPay, ivWxPay;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_deposit;
    }

    @Override
    protected void initData() {
        initTitle("缴纳押金");
        findViewById(R.id.ll_al_pay).setOnClickListener(clickListener);
        findViewById(R.id.ll_wx_pay).setOnClickListener(clickListener);
        ivAlPay = findViewById(R.id.iv_al_pay);
        ivWxPay = findViewById(R.id.iv_wx_pay);
    }

    @Override
    public void onCheckDoubleClick(View v) {
        super.onCheckDoubleClick(v);
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
