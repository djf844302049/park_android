package com.anyidc.cloudpark.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.IntentKey;
import com.anyidc.cloudpark.utils.LicenseKeyboardUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/5.
 */

public class CarMonitorActivity extends BaseActivity implements TextWatcher {
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tvTip;
    private TextView tvConfirm;
    private EditText etNum;
    private List<TextView> tvList;
    private int type = 0;//0表示车辆监控  1表示车位锁操作（管理员界面操作界面）
    private LicenseKeyboardUtil keyboardUtil;


    /**
     * @param context
     * @param type    0表示车辆监控  1表示车位锁操作（管理员界面操作界面）
     */
    public static void start(Context context, int type) {
        Intent intent = new Intent(context, CarMonitorActivity.class);
        intent.putExtra(IntentKey.INTENT_KEY_INT, type);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_monitor;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        type = getIntent().getIntExtra(IntentKey.INTENT_KEY_INT, 0);
        tv1 = findViewById(R.id.tv_num_1);
        tv2 = findViewById(R.id.tv_num_2);
        tv3 = findViewById(R.id.tv_num_3);
        tv4 = findViewById(R.id.tv_num_4);
        tv5 = findViewById(R.id.tv_num_5);
        tv6 = findViewById(R.id.tv_num_6);
        tvTip = findViewById(R.id.tv_tip);
        tvConfirm = findViewById(R.id.btn_watch_camera);
        tvList = new ArrayList<>();
        tvList.add(tv1);
        tvList.add(tv2);
        tvList.add(tv3);
        tvList.add(tv4);
        tvList.add(tv5);
        tvList.add(tv6);
        etNum = findViewById(R.id.et_num);
        keyboardUtil = new LicenseKeyboardUtil(CarMonitorActivity.this, etNum, 0);
        etNum.addTextChangedListener(this);
        etNum.setOnTouchListener((v, event) -> {
            keyboardUtil.showKeyboard();
            return false;
        });
        findViewById(R.id.rl_root).setOnClickListener(v -> {
            if (keyboardUtil.isShow()) {
                keyboardUtil.hideKeyboard();
            }
        });
        tvConfirm.setOnClickListener(clickListener);
        if (type == 0) {
            initTitle("车辆监控");
            tvTip.setText(R.string.input_watch_car_monitor);
            tvConfirm.setText(R.string.watch);
        } else {
            initTitle("选择车位");
            tvTip.setText(R.string.input_opt_park_num);
            tvConfirm.setText(R.string.common_confirm);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        setNum(editable.toString());
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
    public void onCheckDoubleClick(View view) {
        String parkNum = etNum.getText().toString();
        if (TextUtils.isEmpty(parkNum) || parkNum.length() != 6) {
            return;
        }
        if (type == 0) {
//            watchCamera(parkNum);
            startActivity(new Intent(this, MonitorVideoActivity.class));
        } else {
            OptParkLockActivity.start(CarMonitorActivity.this, parkNum, OptParkLockActivity.FROMMANAGER);
        }
    }

    private void watchCamera(String parkNum) {

        getTime(Api.getDefaultService().watchCamera(parkNum)
                , new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {

                    }
                });
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
        etNum.removeTextChangedListener(this);
        super.onDestroy();
    }
}
