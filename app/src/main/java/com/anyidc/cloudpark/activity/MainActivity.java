package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.view.View;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.InitBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        findViewById(R.id.tv_search_place).setOnClickListener(this);
        getTime(Api.getDefaultService().appInit()
                , new RxObserver<BaseEntity<InitBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<InitBean> initBean) {

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_place:
                startActivity(new Intent(this, SearchMapActivity.class));
                break;
        }
    }
}
