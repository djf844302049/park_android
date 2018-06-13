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
    private TextView tvRight, tvPayResult, tvPayTip;

    public static void actionStart(Context context, int result, String num) {
        Intent intent = new Intent(context, PayResultActivity.class);
        intent.putExtra("result", result);
        intent.putExtra("num", num);
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
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("完成");
        tvPayResult = findViewById(R.id.tv_pay_result);
        tvPayTip = findViewById(R.id.tv_pay_result_tip);
        int result = getIntent().getIntExtra("result", 0);
        switch (result) {
            case 1:
                String num = getIntent().getStringExtra("num");
                tvPayResult.setText("支付成功！");
                tvPayTip.setText("您已成功支付￥" + num);
                break;
            case 2:
                tvPayResult.setText("支付失败！");
                tvPayTip.setText("支付失败，请返回支付界面\n重新进行支付。");
                break;
        }
        tvRight.setOnClickListener(clickListener);
        findViewById(R.id.tv_go_center).setOnClickListener(clickListener);
    }

    @Override
    public void onCheckDoubleClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                finish();
                break;
            case R.id.tv_go_center:
                Intent intent = new Intent(this, MineActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                finish();
                break;
        }
    }
}
