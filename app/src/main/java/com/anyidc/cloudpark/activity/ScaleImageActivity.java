package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.WindowManager;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.wiget.ZoomImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class ScaleImageActivity extends BaseActivity {

    private ZoomImageView imageView;

    public static void actionStart(Context context, String imgUrl) {
        Intent intent = new Intent(context, ScaleImageActivity.class);
        intent.putExtra("imgUrl", imgUrl);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scale_image;
    }

    @Override
    protected void initData() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageView = findViewById(R.id.zoom_image);
        String imgUrl = getIntent().getStringExtra("imgUrl");
        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(this).load(imgUrl).placeholder(R.drawable.img_place_holder).into(new GlideDrawableImageViewTarget(imageView) {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                    imageView.setTouchAble(true);
                    super.onResourceReady(resource, animation);
                }
            });
        }
    }
}
