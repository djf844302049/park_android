package com.anyidc.cloudpark.activity;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.anyidc.cloudpark.R;

/**
 * Created by linwenxiong on 2018/4/17.
 */

public class PayAppointmentActivity extends BaseActivity {

    private TextView tvTitle,tvUnitNum,tvAppointmentTime,tvShareTime;
    private LinearLayout llShareTip;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_park;
    }

    @Override
    protected void initData() {

    }
}
