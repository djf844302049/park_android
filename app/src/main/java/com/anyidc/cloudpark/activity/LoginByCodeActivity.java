package com.anyidc.cloudpark.activity;

import android.view.View;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/2/7.
 */

public class LoginByCodeActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_code_login;
    }

    @Override
    protected void initData() {
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.tv_get_code).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
