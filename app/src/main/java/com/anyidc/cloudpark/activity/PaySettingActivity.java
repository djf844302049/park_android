package com.anyidc.cloudpark.activity;

import android.view.View;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

/**
 * Created by Administrator on 2018/3/13.
 */

public class PaySettingActivity extends BaseActivity implements View.OnClickListener {
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
        findViewById(R.id.ll_modify_pay_key).setOnClickListener(this);
        findViewById(R.id.ll_find_back_pay_key).setOnClickListener(this);
        findViewById(R.id.ll_pay_without_key).setOnClickListener(this);
        checkPayKey();
    }

    @Override
    public void onClick(View v) {
//        if (!isSetPayKey) {
//            LoginByCodeActivity.actionStart(this, 1);
//            return;
//        }
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

    private void checkPayKey() {
        getTime(Api.getDefaultService().isSetPayKey()
                , new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        isSetPayKey = true;
                    }
                });
    }
}
