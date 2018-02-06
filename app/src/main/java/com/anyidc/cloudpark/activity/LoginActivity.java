package com.anyidc.cloudpark.activity;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/2/6.
 */

public class LoginActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        initTitle(getString(R.string.login));
    }
}
