package com.anyidc.cloudpark.activity;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/3/16.
 */

public class BindBankCardActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_bank_card;
    }

    @Override
    protected void initData() {
        initTitle("添加银行卡");
    }
}
