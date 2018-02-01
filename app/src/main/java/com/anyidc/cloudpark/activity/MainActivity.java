package com.anyidc.cloudpark.activity;

import android.util.Log;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.TimeBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        Api.getDefaultService()
                .getTime()
                .compose(this.<BaseEntity<TimeBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxObserver<BaseEntity<TimeBean>>(this, true) {

                    @Override
                    public void onSuccess(BaseEntity<TimeBean> timeBeanBaseEntity) {
                        Log.e("-->>", timeBeanBaseEntity.getData().getTime());
                    }
                });
    }
}
