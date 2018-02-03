package com.anyidc.cloudpark.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import com.trello.rxlifecycle2.components.RxActivity;

import java.io.File;

/**
 * Created by necer on 2017/6/29.
 */

public abstract class BaseActivity extends RxActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    public void updateImg(File file) {

    }
}



