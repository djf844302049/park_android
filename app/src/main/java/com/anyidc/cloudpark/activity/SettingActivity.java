package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
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
        findViewById(R.id.ll_go_grade).setOnClickListener(this);
        findViewById(R.id.ll_about_us).setOnClickListener(this);
        String versionName = (String) SpUtils.get(SpUtils.VERSION_NAME, "");
        ((TextView) findViewById(R.id.tv_version_name)).setText(versionName);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_check_update:
                break;
            case R.id.ll_go_grade:
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    ToastUtil.showToast("您的手机没有安装Android应用市场", Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
                break;
            case R.id.ll_about_us:
                break;
        }
    }
}
