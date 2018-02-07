package com.anyidc.cloudpark.activity;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        initStatuBarColor(getResources().getColor(R.color.top_blue));
        getTime(Api.getDefaultService().appInit()
                , new RxObserver<BaseEntity<List<String>>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<List<String>> stringBaseEntity) {

                    }
                });
    }

}
