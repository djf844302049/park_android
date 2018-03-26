package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.LoginRegisterBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.CacheData;
import com.anyidc.cloudpark.utils.CountDownRunnable;

/**
 * Created by Administrator on 2018/2/7.
 */

public class LoginByCodeActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private EditText etPhoneNum;
    private EditText etConfirmCode;
    private TextView tvGetCode;
    private Button btnLogin;
    private String phoneNum;
    private int from;

    public static void actionStart(Context context, int from) {
        Intent intent = new Intent(context, LoginByCodeActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_code_login;
    }

    @Override
    protected void initData() {
        from = getIntent().getIntExtra("from", 0);
        etPhoneNum = findViewById(R.id.et_phone_num);
        etPhoneNum.addTextChangedListener(this);
        etConfirmCode = findViewById(R.id.et_confirm_code);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        tvGetCode = findViewById(R.id.tv_get_code);
        tvGetCode.setOnClickListener(this);
        tvGetCode.setEnabled(false);
        switch (from) {
            case 0:
                btnLogin.setText("登录");
                initTitle("短信验证码登录");
                break;
            default:
                btnLogin.setText("验证");
                initTitle("身份验证");
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.btn_login:
                switch (from) {
                    case 0:
                        login();
                        break;
                    default:
                        idConfirm();
                        break;
                }
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
                        CacheData.setInfoBean(loginRegisterBean.getData());
                        if (CacheData.getIsManager() == 1) {
                            ManagerActivity.start(LoginByCodeActivity.this);
                        } else {
                            startActivity(new Intent(LoginByCodeActivity.this, MainActivity.class));
                        }
                    }
                });
    }

    public void idConfirm() {
        if (TextUtils.isEmpty(phoneNum)) {
            return;
        }
        String code = etConfirmCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            return;
        }
        getTime(Api.getDefaultService().idConfirm(phoneNum, code)
                , new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        PayKeySetActivity.actionStart(LoginByCodeActivity.this, from);
                        finish();
                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (etPhoneNum.getText().toString().length() == 11) {
            tvGetCode.setEnabled(true);
        } else {
            tvGetCode.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        etPhoneNum.removeTextChangedListener(this);
    }
}
