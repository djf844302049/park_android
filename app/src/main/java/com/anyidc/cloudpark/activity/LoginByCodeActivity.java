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
import com.anyidc.cloudpark.utils.CountDownRunnable;
import com.anyidc.cloudpark.utils.SpUtils;

/**
 * Created by Administrator on 2018/2/7.
 */

public class LoginByCodeActivity extends BaseActivity implements View.OnClickListener {
    private EditText etPhoneNum;
    private EditText etConfirmCode;
    private TextView tvGetCode;
    private String phoneNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_code_login;
    }

    @Override
    protected void initData() {
        etPhoneNum = findViewById(R.id.et_phone_num);
        etConfirmCode = findViewById(R.id.et_confirm_code);
        findViewById(R.id.btn_login).setOnClickListener(this);
        tvGetCode = findViewById(R.id.tv_get_code);
        tvGetCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void getCode() {
        phoneNum = etPhoneNum.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            return;
        }
        getTime(Api.getDefaultService().getCode(phoneNum)
                , new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        new CountDownRunnable(120, tvGetCode).run();
                    }
                });
    }

    private void login() {
        String phoneNum = etPhoneNum.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            return;
        }
        String code = etConfirmCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            return;
        }
        getTime(Api.getDefaultService().loginByCode(phoneNum, code)
                , new RxObserver<BaseEntity<LoginRegisterBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<LoginRegisterBean> loginRegisterBean) {
                        SpUtils.set(SpUtils.TOKEN, loginRegisterBean.getData().getToken());
                        startActivity(new Intent(LoginByCodeActivity.this,MainActivity.class));
                    }
                });
    }
}
