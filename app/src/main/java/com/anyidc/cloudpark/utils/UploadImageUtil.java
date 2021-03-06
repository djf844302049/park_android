package com.anyidc.cloudpark.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.BottomSheetDialog;
import android.widget.ImageView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.activity.BaseActivity;
import com.anyidc.cloudpark.activity.CutImageActivity;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Administrator on 2018/2/23.
 */

public class UploadImageUtil {
    private static final int REQUEST_CODE_PICK = 1;
    private static final int REQUEST_CODE_CAPTURE = 3;
    private static final int REQUEST_CODE_CUTTING = 2;

    private Reference<BaseActivity> reference;
    private float scale;
    private ImageView view;

    public UploadImageUtil(BaseActivity activity, ImageView view) {
        this.view = view;
        this.scale = ((float) view.getWidth()) / view.getHeight();
        reference = new WeakReference(activity);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PICK:
            case REQUEST_CODE_CAPTURE:
                if (resultCode == reference.get().RESULT_OK) {
                    List<String> pathList = Album.parseResult(data);
                    CutImageActivity.actionStart(reference.get(), pathList.get(0), REQUEST_CODE_CUTTING, scale);
                }
                break;
            case REQUEST_CODE_CUTTING:
                if (resultCode == reference.get().RESULT_OK) {
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
        view.setImageBitmap(bitmap);
        reference.get().updateImg(file);
    }

    /**
     * 头像控件点击触发的事件
     */
    public void uploadHeadPhoto() {
        BottomSheetDialog dialog = new BottomSheetDialog(reference.get(), R.style.dialog);
        dialog.setContentView(R.layout.layout_bottom_choice);
        dialog.findViewById(R.id.tv_ahead).setOnClickListener(view1 -> {
                    AndPermission.with(reference.get())
                            .permission(android.Manifest.permission.CAMERA
                                    , android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .onGranted(permissions -> Album.camera(reference.get()).start(REQUEST_CODE_CAPTURE))
                            .onDenied(permissions -> new PermissionSetting(reference.get()).showSetting(permissions))
                            .start();
                    dialog.dismiss();
                }
        );
        dialog.findViewById(R.id.tv_bottom).setOnClickListener(view1 -> {
                    AndPermission.with(reference.get())
                            .permission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            .onGranted(permissions -> {
                                Album.album(reference.get())
                                        .toolBarColor(reference.get().getResources().getColor(R.color.top_blue)) // Toolbar 颜色，默认蓝色。
                                        .statusBarColor(reference.get().getResources().getColor(R.color.top_blue)) // StatusBar 颜色，默认蓝色。
                                        .navigationBarColor(reference.get().getResources().getColor(R.color.top_blue)) // NavigationBar 颜色，默认黑色，建议使用默认。
                                        .title("图库") // 配置title。
                                        .selectCount(1) // 最多选择几张图片。
                                        .columnCount(2) // 相册展示列数，默认是2列。
                                        .camera(false) // 是否有拍照功能。
                                        .start(REQUEST_CODE_PICK); // 999是
                            })
                            .onDenied(permissions -> new PermissionSetting(reference.get()).showSetting(permissions))
                            .start();
                    dialog.dismiss();
                }
        );
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(view1 -> dialog.dismiss());
        dialog.show();
    }
}
