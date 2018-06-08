package com.anyidc.cloudpark.utils;

import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2018/2/10.
 */

public class FiveMinuCountDownRunnable implements Runnable {
    private TextView tvTime;
    private Button btn;
    private long countDownTime = 5 * 60 * 1000;

    public FiveMinuCountDownRunnable(TextView tvTime, Button btn) {
        this.tvTime = tvTime;
        this.btn = btn;
    }

    @Override
    public void run() {
        if (countDownTime > 0) {
            String remain = new SimpleDateFormat("mm:ss").format(countDownTime);
            tvTime.setText("支付剩余时间  " + remain);
            countDownTime -= 1000;
            tvTime.postDelayed(this, 1000);
        } else {
            tvTime.setText("支付剩余时间  00:00");
            btn.setEnabled(false);
            btn.setText("支付超时，请返回重新预约支付");
        }
    }
}
