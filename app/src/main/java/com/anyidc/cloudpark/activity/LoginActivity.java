package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/2/6.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText etPhoneNum;
    private EditText etPassword;
    private TextView tvRight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        initTitle(getString(R.string.login));
        etPhoneNum = findViewById(R.id.et_phone_num);
        etPassword = findViewById(R.id.et_password);
        tvRight = findViewById(R.id.tv_right);
        tvRight.setText("注册");
        tvRight.setOnClickListener(this);
        findViewById(R.id.tv_login_by_code).setOnClickListener(this);
        findViewById(R.id.tv_forget_password).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_by_code:
                startActivity(new Intent(this, LoginByCodeActivity.class));
                break;
            case R.id.tv_forget_password:
                break;
            case R.id.btn_login:
                break;
            case R.id.tv_right:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
