package com.anyidc.cloudpark.utils;

import android.widget.TextView;

/**
 * Created by Administrator on 2018/2/10.
 */

public class CountDownRunnable implements Runnable {
    private TextView tvGetCode;
    private int countDownTime;
    private int time;

    public CountDownRunnable(int countDownTime, TextView tvGetCode) {
        time = countDownTime;
        this.tvGetCode = tvGetCode;
        this.countDownTime = countDownTime;
    }

    @Override
    public void run() {
        if (countDownTime > 0) {
            tvGetCode.setEnabled(false);
            tvGetCode.setText(countDownTime + "s");
            countDownTime--;
            tvGetCode.postDelayed(this, 1000);
        } else {
            tvGetCode.setEnabled(true);
            tvGetCode.setText("获取验证码");
            countDownTime = time;
        }
    }
}
