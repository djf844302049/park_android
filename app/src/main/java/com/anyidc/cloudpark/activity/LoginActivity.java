package com.anyidc.cloudpark.activity;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/2/6.
 */

public class LoginActivity extends BaseActivity {
    private EditText etPhoneNum;
    private EditText etPassword;
    private TextView tvRight, tvStatement;

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
        tvStatement = findViewById(R.id.tv_statement);
        String message = "登录代表您已同意《云能停车用户条款》";
        SpannableString spannableString = new SpannableString(message);
        Pattern pattern = Pattern.compile("云能停车用户条款");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            ClickableSpan what = new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    WebViewActivity.actionStart(LoginActivity.this, Api.BASE_URL + "/admin/showhtml/showUserTerm");
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                }
            };
            spannableString.setSpan(what, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#0d80f7")),
                9, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvStatement.setText(spannableString);
        tvStatement.setMovementMethod(LinkMovementMethod.getInstance());
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
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                });
    }
}
