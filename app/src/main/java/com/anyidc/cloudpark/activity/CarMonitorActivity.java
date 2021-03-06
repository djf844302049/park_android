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
import com.anyidc.cloudpark.moduel.MonitorVideoBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.CacheData;
import com.anyidc.cloudpark.utils.IntentKey;
import com.anyidc.cloudpark.utils.LicenseKeyboardUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/5.
 */

public class CarMonitorActivity extends BaseActivity implements TextWatcher {
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
        TextView tv1 = findViewById(R.id.tv_num_1);
        TextView tv2 = findViewById(R.id.tv_num_2);
        TextView tv3 = findViewById(R.id.tv_num_3);
        TextView tv4 = findViewById(R.id.tv_num_4);
        TextView tv5 = findViewById(R.id.tv_num_5);
        TextView tv6 = findViewById(R.id.tv_num_6);
        TextView tvTip = findViewById(R.id.tv_tip);
        TextView tvConfirm = findViewById(R.id.btn_watch_camera);
        tvList = new ArrayList<>();
        tvList.add(tv1);
        tvList.add(tv2);
        tvList.add(tv3);
        tvList.add(tv4);
        tvList.add(tv5);
        tvList.add(tv6);
        etNum = findViewById(R.id.et_num);//输入框
        keyboardUtil = new LicenseKeyboardUtil(CarMonitorActivity.this, etNum, 0);//new一个键盘方法类
        etNum.addTextChangedListener(this);
        //点击输入框显示键盘
        etNum.setOnTouchListener((v, event) -> {
            keyboardUtil.showKeyboard();
            return false;
        });
        //点击屏幕上除输入框外任何位置监听：隐藏键盘
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
    //让每个框对应输入的字符
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
        /*
        * 0的话则打开监控
        * */
        if (type == 0) {
            switch (CacheData.getIsManager()) {
                case 1:
                    watchCamera(parkNum);
                    break;
                default:
                    userWatchCamera(parkNum);
                    break;
            }
        } else {
            OptParkLockActivity.start(CarMonitorActivity.this, parkNum, OptParkLockActivity.FROMMANAGER);//1的话打开操作车位锁
        }
    }
    //OptParkLockActivity.start(CarMonitorActivity.this,parkNum,OptParkLockActivity.FROMMANAGER);

    private void watchCamera(String parkNum) {

        getTime(Api.getDefaultService().watchCamera(parkNum)
                , new RxObserver<BaseEntity<MonitorVideoBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<MonitorVideoBean> baseEntity) {
                        MonitorVideoActivity.actionStart(CarMonitorActivity.this, baseEntity.getData().getPlay_address());
                    }
                });
    }

    private void userWatchCamera(String parkNum) {

        getTime(Api.getDefaultService().userWatchCamera(parkNum)
                , new RxObserver<BaseEntity<MonitorVideoBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<MonitorVideoBean> baseEntity) {
                        MonitorVideoActivity.actionStart(CarMonitorActivity.this, baseEntity.getData().getPlay_address());
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
