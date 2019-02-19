package com.anyidc.cloudpark.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.anyidc.cloudpark.BaseApplication;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.IsIllegalBean;
import com.anyidc.cloudpark.moduel.TimeBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.ApiService;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.CheckDoubleClickListener;
import com.anyidc.cloudpark.utils.OnCheckDoubleClick;
import com.anyidc.cloudpark.utils.ScreenFitUtil;
import com.anyidc.cloudpark.utils.SpUtils;
import com.anyidc.cloudpark.utils.ToastUtil;
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
    protected AlertDialog dialog;
    private AlertReceiver receiver;
    private final static int ISILLEGA = 369;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置无ActionBar
        ScreenFitUtil.setCustomDensity(this, getApplication());
        setContentView(getLayoutId());
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.top_blue), true);
        clickListener = new CheckDoubleClickListener(this);
        try {
            findViewById(R.id.iv_back).setOnClickListener(new CheckDoubleClickListener(view -> finish()));
            tvTitle = findViewById(R.id.tv_title);
        } catch (Exception e) {
            Log.e(TAG, "控件不存在！！！");
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        dialog = new AlertDialog.Builder(this)
                .setTitle("用户违规损坏详情：")
                .setMessage("告知：因您违规操作，导致设备损坏，须照价赔偿，具体赔偿办法请查看违规情况。如有疑问，请联系客服电话：0592-2932522")
                .setCancelable(false)
                .create();
        receiver = new AlertReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.anyidc.alert.RECEIVER");
        registerReceiver(receiver, intentFilter);
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

    @SuppressLint("CheckResult")
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

    }

    protected void isIllegal() {
        Api.getDefaultService()
                .getTime()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxObserver<BaseEntity<TimeBean>>(this, false) {

                    @Override
                    public void onSuccess(BaseEntity<TimeBean> timeBeanBaseEntity) {
                        SpUtils.set(SpUtils.TIME, timeBeanBaseEntity.getData().getTime());
                        Api.getDefaultService().isIllegal().compose(BaseActivity.this.bindToLifecycle())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new RxObserver<BaseEntity<IsIllegalBean>>(BaseActivity.this, false) {
                                    @Override
                                    public void onSuccess(BaseEntity<IsIllegalBean> isIllegalBean) {
                                        IsIllegalBean data = isIllegalBean.getData();
                                        if (data.getIsIllegal() == 1) {
                                            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "缴纳违规罚款", (dialogInterface, i) ->
                                                    WebViewActivity.actionStartForResult(BaseActivity.this, data.getIll_url(), ISILLEGA)
                                            );
                                            if (!dialog.isShowing()) {
                                                dialog.show();
                                            }
                                        }
                                    }
                                });
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ISILLEGA||requestCode==MineActivity.LOGIN && resultCode == RESULT_OK) {
            isIllegal();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        BaseApplication.getInstance().removeActivity(this);
    }

    public class AlertReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (BaseApplication.getInstance().getTopActivity() == BaseActivity.this) {
                String path = intent.getStringExtra("url");
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "缴纳违规罚款", (dialogInterface, i) ->
                        WebViewActivity.actionStartForResult(BaseActivity.this, path, ISILLEGA)
                );
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        }
    }
}



