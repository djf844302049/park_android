package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.LoginRegisterBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.AesUtil;
import com.anyidc.cloudpark.utils.CacheData;
import com.anyidc.cloudpark.utils.CountDownRunnable;
import com.anyidc.cloudpark.utils.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/2/7.
 */

public class RegisterActivity extends BaseActivity implements TextWatcher {
    private EditText etPhoneNum;
    private EditText etConfirmCode;
    private EditText etPassword;
    private TextView tvGetCode;
    private CheckBox cbStatement;
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
        cbStatement = findViewById(R.id.cb_statement);
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
                cbStatement.setVisibility(View.GONE);
                break;
            default:
                initTitle(getString(R.string.register));
                String message = "我同意《云能停车用户条款》";
                SpannableString spannableString = new SpannableString(message);
                Pattern pattern = Pattern.compile("云能停车用户条款");
                Matcher matcher = pattern.matcher(message);
                while (matcher.find()) {
                    ClickableSpan what = new ClickableSpan() {

                        @Override
                        public void onClick(View widget) {
                            WebViewActivity.actionStart(RegisterActivity.this, Api.DEBUG_URL + "/admin/showhtml/showUserTerm", 2);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            ds.setUnderlineText(false);
                        }
                    };
                    spannableString.setSpan(what, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#0d80f7")),
                        4, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                cbStatement.setText(spannableString);
                cbStatement.setMovementMethod(LinkMovementMethod.getInstance());
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
        if (!cbStatement.isChecked()) {
            ToastUtil.showToast("请仔细阅读用户协议，并确认同意后再进行注册操作", Toast.LENGTH_SHORT);
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
