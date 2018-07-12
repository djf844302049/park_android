package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.InitBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.LoginUtil;
import com.anyidc.cloudpark.utils.SpUtils;
import com.anyidc.cloudpark.utils.ToastUtil;

/**
 * Created by Administrator on 2018/2/24.
 */

public class SettingActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        initTitle("设置");
        findViewById(R.id.ll_check_update).setOnClickListener(clickListener);
        findViewById(R.id.ll_about_us).setOnClickListener(clickListener);
        findViewById(R.id.ll_logout).setOnClickListener(clickListener);
        String versionName = (String) SpUtils.get(SpUtils.VERSION_NAME, "");
        ((TextView) findViewById(R.id.tv_version_name)).setText(versionName);
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.ll_check_update:
                checkUpdate();
                break;
            case R.id.ll_about_us:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.ll_logout:
                LoginUtil.logout();
                finish();
                break;
        }
    }

    private void checkUpdate() {
        getTime(Api.getDefaultService().appInit()
                , new RxObserver<BaseEntity<InitBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<InitBean> initBean) {
                        InitBean data = initBean.getData();
                        if (data.getIs_update() == 0) {
                            ToastUtil.showToast("当前已为最新版本，无需更新", Toast.LENGTH_SHORT);
                        } else {
                            new AlertDialog.Builder(SettingActivity.this)
                                    .setTitle("提示")
                                    .setMessage("云能只能停车有新版本啦，是否前往更新？")
                                    .setPositiveButton("确定", (dialog, which) -> {
                                        if (TextUtils.isEmpty(data.getDownload_url()))
                                            openApplicationMarket();
                                        else {
                                            openLinkBySystem(data.getDownload_url());
                                        }
                                    })
                                    .setNegativeButton("取消", null).show();

                        }
                    }
                });
    }

    private void openApplicationMarket() {
        try {
            String str = "market://details?id=" + getPackageName();
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setData(Uri.parse(str));
            startActivity(localIntent);
        } catch (Exception e) {
            // 打开应用商店失败 可能是没有手机没有安装应用市场
            e.printStackTrace();
        }
    }

    private void openLinkBySystem(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
