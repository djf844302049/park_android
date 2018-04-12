package com.anyidc.cloudpark.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.anyidc.cloudpark.BaseApplication;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.TimeBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.CheckDoubleClickListener;
import com.anyidc.cloudpark.utils.OnCheckDoubleClick;
import com.anyidc.cloudpark.utils.SpUtils;
import com.githang.statusbar.StatusBarCompat;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by necer on 2017/6/29.
 */

public abstract class BaseActivity<T> extends RxFragmentActivity implements OnCheckDoubleClick {
    private TextView tvTitle;
    protected final String TAG = getClass().getSimpleName();
    protected CheckDoubleClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置无ActionBar
        setContentView(getLayoutId());
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.top_blue), true);
        clickListener = new CheckDoubleClickListener(this);
        try {
            findViewById(R.id.iv_back).setOnClickListener(clickListener);
            tvTitle = findViewById(R.id.tv_title);
        } catch (Exception e) {
            Log.e(TAG, "控件不存在！！！");
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        initData();
    }

    protected void initTitle(String title) {
        if (tvTitle != null)
            tvTitle.setText(title);
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    public void updateImg(File file) {

    }

    public void getTime(Observable<BaseEntity<T>> observable, RxObserver<BaseEntity<T>> observer) {
        Api.getDefaultService()
                .getTime()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxObserver<BaseEntity<TimeBean>>(this, false) {

                    @Override
                    public void onSuccess(BaseEntity<TimeBean> timeBeanBaseEntity) {
                        SpUtils.set(SpUtils.TIME, timeBeanBaseEntity.getData().getTime());
                        observable.compose(BaseActivity.this.bindToLifecycle())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(observer);
                    }
                });
    }

    @Override
    public void onCheckDoubleClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().removeActivity(this);
    }
}



