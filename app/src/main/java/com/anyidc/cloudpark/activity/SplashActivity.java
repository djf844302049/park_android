package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.os.Handler;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/3/14.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        avoidRenewLaunchActivity();
        start();
    }

    /**
     * 避免首次安装点击打开启动程序后，按home键返回桌面然后点击桌面图标会重新实例化入口类的activity，
     * 原因参考http://www.cnblogs.com/net168/p/5722752.html
     */
    private boolean avoidRenewLaunchActivity() {
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return true;
                }
            }
        }
        return false;
    }

    private void start() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 3000);
    }
}
