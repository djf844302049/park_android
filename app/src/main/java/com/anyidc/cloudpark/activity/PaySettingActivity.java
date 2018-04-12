package com.anyidc.cloudpark.activity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.utils.CacheData;
import com.anyidc.cloudpark.utils.ToastUtil;

/**
 * Created by Administrator on 2018/3/13.
 */

public class PaySettingActivity extends BaseActivity{
    private TextView tvPayStatus;
    private boolean isSetPayKey;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_setting;
    }

    @Override
    protected void initData() {
        initTitle("支付设置");
        tvPayStatus = findViewById(R.id.tv_pay_status);
        findViewById(R.id.ll_modify_pay_key).setOnClickListener(clickListener);
        findViewById(R.id.ll_find_back_pay_key).setOnClickListener(clickListener);
        findViewById(R.id.ll_pay_without_key).setOnClickListener(clickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int freePay = CacheData.isFreePay();
        isSetPayKey = CacheData.getSurplusPassword() == 1;
        if (!isSetPayKey) {
            ToastUtil.showToast("您还未设置支付密码", Toast.LENGTH_SHORT);
        }
        if (freePay == 1) {
            tvPayStatus.setText("已开启");
        } else {
            tvPayStatus.setText("未开启");
        }
    }

    @Override
    public void onCheckDoubleClick(View v) {
        if (!isSetPayKey) {
            LoginByCodeActivity.actionStart(this, 1);
            return;
        }
        switch (v.getId()) {
            case R.id.ll_modify_pay_key:
                PayKeySetActivity.actionStart(this, 2);
                break;
            case R.id.ll_find_back_pay_key:
                LoginByCodeActivity.actionStart(this, 3);
                break;
            case R.id.ll_pay_without_key:
                PayKeySetActivity.actionStart(this, 4);
                break;
        }
    }
}
