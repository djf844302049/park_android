package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.LoginRegisterBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.AesUtil;
import com.anyidc.cloudpark.utils.CacheData;
import com.anyidc.cloudpark.utils.CountDownRunnable;

/**
 * Created by Administrator on 2018/2/7.
 */

public class RegisterActivity extends BaseActivity implements TextWatcher {
    private EditText etPhoneNum;
    private EditText etConfirmCode;
    private EditText etPassword;
    private TextView tvGetCode;
    private LinearLayout llCb;
    private Button btnCommit;
    private String phoneNum;
    private int from;

    public static void actionStart(Context context, int from) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        llCb = findViewById(R.id.ll_cb);
        etPhoneNum = findViewById(R.id.et_phone_num);
        etPhoneNum.addTextChangedListener(this);
        etConfirmCode = findViewById(R.id.et_confirm_code);
        etPassword = findViewById(R.id.et_password);
        tvGetCode = findViewById(R.id.tv_get_code);
        tvGetCode.setOnClickListener(clickListener);
        tvGetCode.setEnabled(false);
        btnCommit = findViewById(R.id.btn_register);
        btnCommit.setOnClickListener(clickListener);
        from = getIntent().getIntExtra("from", 0);
        switch (from) {
            case 1:
                initTitle("找回密码");
                btnCommit.setText("完成");
                llCb.setVisibility(View.GONE);
                break;
            default:
                initTitle(getString(R.string.register));
                break;
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.btn_register:
                switch (from) {
                    case 1:
                        resetPassword();
                        break;
                    default:
                        register();
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

    private void register() {
        if (TextUtils.isEmpty(phoneNum)) {
            return;
        }
        String code = etConfirmCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            return;
        }
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            return;
        }
        getTime(Api.getDefaultService().register(phoneNum, AesUtil.encrypt(password), code),
                new RxObserver<BaseEntity<LoginRegisterBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<LoginRegisterBean> loginRegisterBean) {
                        CacheData.setInfoBean(loginRegisterBean.getData());
                        startActivity(new Intent(RegisterActivity.this, CompleteBaseInfoActivity.class));
                        finish();
                    }
                });
    }

    private void resetPassword() {
        if (TextUtils.isEmpty(phoneNum)) {
            return;
        }
        String code = etConfirmCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            return;
        }
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            return;
        }
        getTime(Api.getDefaultService().forgetPassword(phoneNum, AesUtil.encrypt(password), code),
                new RxObserver<BaseEntity<LoginRegisterBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<LoginRegisterBean> loginRegisterBean) {
//                        CacheData.setInfoBean(loginRegisterBean.getData());
                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (etPhoneNum.getText().toString().length() != 11) {
            tvGetCode.setEnabled(false);
        } else {
            tvGetCode.setEnabled(true);
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
