package com.anyidc.cloudpark.activity;

import android.widget.Switch;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

/**
 * Created by Administrator on 2018/3/15.
 */

public class PayWithoutKeyActivity extends BaseActivity {
    private int freePassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_without_key_setting;
    }

    @Override
    protected void initData() {
        initTitle("设置小额免密");
        Switch mSwitch = findViewById(R.id.switch_pay_without_key);
        mSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                freePassword = 1;
            } else {
                freePassword = 0;
            }
            setPayWithoutKey();
        });
    }

    private void setPayWithoutKey() {
        getTime(Api.getDefaultService().setFreePay(freePassword)
                , new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {

                    }
                });
    }
}
