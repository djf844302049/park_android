package com.anyidc.cloudpark.activity;

import android.util.Log;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.SpUtils;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        getTime();
    }

    @Override
    protected void getNetData() {
        Api.getDefaultService()
                .appInit()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxObserver<BaseEntity<String>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<String> stringBaseEntity) {

                    }
                });
    }
}
