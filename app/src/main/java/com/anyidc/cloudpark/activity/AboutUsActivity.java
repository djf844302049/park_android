package com.anyidc.cloudpark.activity;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/3/22.
 */

public class AboutUsActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initData() {
        initTitle("关于我们");
    }
}
