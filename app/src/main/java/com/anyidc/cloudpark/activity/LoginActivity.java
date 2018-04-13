package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.LoginRegisterBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.AesUtil;
import com.anyidc.cloudpark.utils.CacheData;

/**
 * Created by Administrator on 2018/2/6.
 */

public class LoginActivity extends BaseActivity {
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
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getResources().getString(R.string.register));
        tvRight.setOnClickListener(clickListener);
        findViewById(R.id.tv_login_by_code).setOnClickListener(clickListener);
        findViewById(R.id.tv_forget_password).setOnClickListener(clickListener);
        findViewById(R.id.btn_login).setOnClickListener(clickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CacheData.setInfoBean(null);
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_by_code:
                LoginByCodeActivity.actionStart(this, 0);
                break;
            case R.id.tv_forget_password:
                RegisterActivity.actionStart(this, 1);
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_right:
                RegisterActivity.actionStart(this, 0);
                break;
        }
    }

    private void login() {
        String phoneNum = etPhoneNum.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            return;
        }
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            return;
        }
        getTime(Api.getDefaultService().loginByPassword(phoneNum, AesUtil.encrypt(password))
                , new RxObserver<BaseEntity<LoginRegisterBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<LoginRegisterBean> loginRegisterBean) {
                        CacheData.setInfoBean(loginRegisterBean.getData());
                        if (CacheData.getIsManager() == 1) {
                            ManagerActivity.start(LoginActivity.this);
                        } else {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }
                });
    }
}
