package com.anyidc.cloudpark.activity;

import android.view.View;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/3/13.
 */

public class PaySettingActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_setting;
    }

    @Override
    protected void initData() {
        initTitle("支付设置");
    }

    @Override
    public void onClick(View v) {

    }
}
