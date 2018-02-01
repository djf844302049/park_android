package com.anyidc.cloudpark.activity;

import android.os.Bundle;

import com.trello.rxlifecycle2.components.RxActivity;

/**
 * Created by necer on 2017/6/29.
 */

public abstract class BaseActivity extends RxActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initData();
}



