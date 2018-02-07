package com.anyidc.cloudpark.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

/**
 * Created by Administrator on 2018/2/7.
 */

public class RegisterActivity extends BaseActivity implements OnClickListener {
    private EditText etPhoneNum;
    private EditText etConfirmCode;
    private EditText etPassword;
    private TextView tvGetCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        initTitle(getString(R.string.register));
        etPhoneNum = findViewById(R.id.et_phone_num);
        etConfirmCode = findViewById(R.id.et_confirm_code);
        etPassword = findViewById(R.id.et_password);
        tvGetCode = findViewById(R.id.tv_get_code);
        tvGetCode.setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.btn_register:
                break;
        }
    }

    private void getCode() {
        String phoneNum = etPhoneNum.getText().toString();
        getTime(Api.getDefaultService().getCode(phoneNum)
                , new RxObserver<BaseEntity>(this, true) {
            @Override
            public void onSuccess(BaseEntity baseEntity) {

            }
        });
    }
}