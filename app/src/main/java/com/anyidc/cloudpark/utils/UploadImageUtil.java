package com.anyidc.cloudpark.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.widget.ImageView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.activity.BaseActivity;
import com.anyidc.cloudpark.activity.CutImageActivity;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2018/2/23.
 */

public class UploadImageUtil {
    private static final int REQUEST_CODE_PICK = 1;
    private static final int REQUEST_CODE_CAPTURE = 3;
    private static final int REQUEST_CODE_CUTTING = 2;

    private BaseActivity activity;
    private ImageView view;
    private Rationale mRationale = (context, permissions, executor) -> {
        // 这里使用一个Dialog询问用户是否继续授权。

        // 如果用户继续：
        executor.execute();

        // 如果用户中断：
        executor.cancel();
    };

    public UploadImageUtil(BaseActivity activity, ImageView view) {
        this.activity = activity;
        this.view = view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PICK:
            case REQUEST_CODE_CAPTURE:
                if (resultCode == activity.RESULT_OK) {
                    List<String> pathList = Album.parseResult(data);
                    CutImageActivity.actionStart(activity, pathList.get(0), REQUEST_CODE_CUTTING);
                }
                break;
            case REQUEST_CODE_CUTTING:
                if (resultCode == activity.RESULT_OK) {
                    String path = data.getStringExtra("path");
                    setPicToView(path);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 将裁剪后的图片设置到控件上并且上传图片到服务器
     */
    private void setPicToView(String path) {

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        File file = new File(path);
        Log.e("path", path);
        view.setImageBitmap(bitmap);
//            if (view instanceof SimpleDraweeView) {
//        Glide.with(activity)
//                .load(baos)
//                .into(view);
//            } else {
//                Drawable drawable = new BitmapDrawable(activity.getResources(), photo);
//                view.setImageDrawable(drawable);
//            }
        activity.updateImg(file);
    }

    /**
     * 头像控件点击触发的事件
     */
    public void uploadHeadPhoto() {
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        dialog.setContentView(R.layout.layout_picture_choice);
        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(view1 -> {
                    AndPermission.with(activity)
                            .permission(android.Manifest.permission.CAMERA
                                    , android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .rationale(mRationale)
                            .onGranted(permissions -> Album.camera(activity).start(REQUEST_CODE_CAPTURE))
                            .start();
                    dialog.dismiss();
                }
        );
        dialog.findViewById(R.id.tv_pick_photo).setOnClickListener(view1 -> {
                    AndPermission.with(activity)
                            .permission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            .onGranted(permissions -> {
                                Album.album(activity)
                                        .toolBarColor(activity.getResources().getColor(R.color.top_blue)) // Toolbar 颜色，默认蓝色。
                                        .statusBarColor(activity.getResources().getColor(R.color.top_blue)) // StatusBar 颜色，默认蓝色。
                                        .navigationBarColor(activity.getResources().getColor(R.color.top_blue)) // NavigationBar 颜色，默认黑色，建议使用默认。
                                        .title("图库") // 配置title。
                                        .selectCount(1) // 最多选择几张图片。
                                        .columnCount(2) // 相册展示列数，默认是2列。
                                        .camera(false) // 是否有拍照功能。
                                        .start(REQUEST_CODE_PICK); // 999是
                            })
                            .rationale(mRationale)
                            .start();
                    dialog.dismiss();
                }
        );
        dialog.show();
    }

    private File bytesFile(byte[] bytes) throws IOException {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/", "file.JPG");
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                bos.close();
            }
        }
        return file;
    }
}
