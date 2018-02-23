package com.anyidc.cloudpark.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.utils.ImageTools;
import com.anyidc.cloudpark.wiget.clip.ClipImageLayout;

/**
 * Created by swallow on 2018/2/22.
 */

public class CutImageActivity extends BaseActivity implements View.OnClickListener {

    private ClipImageLayout clipLayout;

    public static void actionStart(Activity context, String imgPath, int requestCode) {
        Intent intent = new Intent(context, CutImageActivity.class);
        intent.putExtra("path", imgPath);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cut_img;
    }

    @Override
    protected void initData() {
        initTitle("图片裁剪");
        clipLayout = findViewById(R.id.clip_img_layout);
        TextView tvRight = findViewById(R.id.tv_right);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("完成");
        tvRight.setOnClickListener(this);
        String path = getIntent().getStringExtra("path");
        Bitmap bitmap = ImageTools.convertToBitmap(path, 1000, 1000);
        clipLayout.setBitmap(bitmap);
    }

    @Override
    public void onClick(View view) {
        Bitmap bitmap = clipLayout.clip();
        String fileName = "file.png";
        String path = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + fileName;
        boolean b = ImageTools.savePhotoToSDCard(bitmap, path);
        if (b) {
            setResult(RESULT_OK, new Intent().putExtra("path", path));
            finish();
        }
    }
}
