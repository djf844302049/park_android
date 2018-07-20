package com.anyidc.cloudpark.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.utils.CacheData;
import com.anyidc.cloudpark.utils.LicenseKeyboardUtil;
import com.anyidc.cloudpark.utils.LoginUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */

public class PayParkActivity extends BaseActivity implements TextWatcher {
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private EditText etNum;
    private List<TextView> tvList;
    private String unitId;
    private LicenseKeyboardUtil keyboardUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_park;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        initTitle("停车付费");
        tv1 = findViewById(R.id.tv_num_1);
        tv2 = findViewById(R.id.tv_num_2);
        tv3 = findViewById(R.id.tv_num_3);
        tv4 = findViewById(R.id.tv_num_4);
        tv5 = findViewById(R.id.tv_num_5);
        tv6 = findViewById(R.id.tv_num_6);
        tvList = new ArrayList<>();
        tvList.add(tv1);
        tvList.add(tv2);
        tvList.add(tv3);
        tvList.add(tv4);
        tvList.add(tv5);
        tvList.add(tv6);
        etNum = findViewById(R.id.et_num);
        keyboardUtil = new LicenseKeyboardUtil(PayParkActivity.this, etNum, 0);
        etNum.addTextChangedListener(this);
        etNum.setOnTouchListener((v, event) -> {
            keyboardUtil.showKeyboard();
            return false;
        });
        findViewById(R.id.btn_go_pay).setOnClickListener(clickListener);
        findViewById(R.id.rl_root).setOnClickListener(v -> {
            if (keyboardUtil.isShow()) {
                keyboardUtil.hideKeyboard();
            }
        });
    }

    @Override
    public void onCheckDoubleClick(View v) {
        if (TextUtils.isEmpty(unitId) || unitId.length() != 6) {
            return;
        }
        ParkChargeActivity.actionStart(this, unitId);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        unitId = s.toString();
        setNum(s.toString());
    }

    private void setNum(String num) {
        char[] chars = num.toCharArray();
        int length = chars.length;
        for (TextView textView : tvList) {
            textView.setText("");
        }
        for (int i = 0; i < length; i++) {
            tvList.get(i).setText(String.valueOf(chars[i]));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyboardUtil.isShow()) {
                keyboardUtil.hideKeyboard();
            } else {
                finish();
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        etNum.removeTextChangedListener(this);
    }
}
