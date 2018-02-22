package com.anyidc.cloudpark.activity;

import android.graphics.BitmapFactory;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.wiget.clip.ClipImageLayout;

/**
 * Created by swallow on 2018/2/22.
 */

public class CutImageActivity extends BaseActivity {

    private ClipImageLayout clipLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cut_img;
    }

    @Override
    protected void initData() {
        clipLayout = findViewById(R.id.clip_img_layout);
        clipLayout.setBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.img_pay_park));
    }
}
