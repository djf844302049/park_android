package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/4/11.
 */

public class PayResultActivity extends BaseActivity {
    private TextView tvRight, tvPayResult, tvPayTip, tvGoCenter;
    private int from;
    public static boolean rechargeDep;

    public static void actionStart(Context context, int result, String num, int from) {
        Intent intent = new Intent(context, PayResultActivity.class);
        intent.putExtra("result", result);
        intent.putExtra("num", num);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void initData() {
        initTitle("支付结果");
        tvRight = findViewById(R.id.tv_right);
        tvPayResult = findViewById(R.id.tv_pay_result);
        tvPayTip = findViewById(R.id.tv_pay_result_tip);
        findViewById(R.id.iv_back).setOnClickListener(clickListener);
        tvRight.setOnClickListener(clickListener);
        tvGoCenter = findViewById(R.id.tv_go_center);
        tvGoCenter.setOnClickListener(clickListener);
        int result = getIntent().getIntExtra("result", 0);
        from = getIntent().getIntExtra("from", 0);
        switch (result) {
            case 1:
                String num = getIntent().getStringExtra("num");
                tvPayResult.setText("支付成功！");
                switch (from) {
                    case 0://缴纳押金
                        tvPayTip.setText("您已成功缴纳押金￥" + num);
                        rechargeDep = true;
                        break;
                    case 1://停车付费
                        tvPayTip.setText("您已成功缴纳停车费￥" + num + "，\n车位锁即将降下，您有10分钟时间离开车位，\n"
                                + "否则车位锁会再次升起，重新进入计费。");
                        break;
                    case 2://预约
                        tvGoCenter.setVisibility(View.VISIBLE);
                        tvPayTip.setText("您已成功缴纳预约费￥" + num + "，\n请您于预约时间点前到达车位,否则车位不予\n" +
                                "保留");
                        break;
                    case 3://充值
                        tvPayTip.setText("您已成功充值￥" + num);
                        break;
                }
                break;
            case 2:
                tvPayResult.setText("支付失败！");
                tvPayTip.setText("支付失败，请返回支付界面\n重新进行支付。");
                break;
        }
    }

    @Override
    public void onCheckDoubleClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                switch (from) {
                    case 1:
                    case 2:
                        startActivity(new Intent(this, MainActivity.class));
                        break;
                }
                finish();
                break;
            case R.id.tv_go_center:
                Intent intent = new Intent(this, AppointmentRecordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
