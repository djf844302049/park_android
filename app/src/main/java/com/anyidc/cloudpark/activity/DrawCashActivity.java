package com.anyidc.cloudpark.activity;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/3/15.
 */

public class DrawCashActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_draw_cash;
    }

    @Override
    protected void initData() {
        initTitle("余额提现");
    }
}
