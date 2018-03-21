package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.net.Uri;
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

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        initTitle("设置");
        findViewById(R.id.ll_check_update).setOnClickListener(this);
        findViewById(R.id.ll_about_us).setOnClickListener(this);
        findViewById(R.id.ll_logout).setOnClickListener(this);
        String versionName = (String) SpUtils.get(SpUtils.VERSION_NAME, "");
        ((TextView) findViewById(R.id.tv_version_name)).setText(versionName);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_check_update:
                checkUpdate();
                break;
            case R.id.ll_about_us:
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
                            openApplicationMarket();
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
}
