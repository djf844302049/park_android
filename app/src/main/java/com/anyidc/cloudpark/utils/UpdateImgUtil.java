package com.anyidc.cloudpark.utils;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.widget.ImageView;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.activity.BaseActivity;
import com.bumptech.glide.Glide;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class UpdateImgUtil {

    private static final int REQUEST_CODE_PICK = 1;
    private static final int REQUEST_CODE_CAPTURE = 3;
    private static final int REQUEST_CODE_CUTTING = 2;
    private File file;
    private Uri uri;

    private BaseActivity activity;
    private ImageView view;
    private Rationale mRationale = (context, permissions, executor) -> {
        // 这里使用一个Dialog询问用户是否继续授权。

        // 如果用户继续：
        executor.execute();

        // 如果用户中断：
        executor.cancel();
    };

    public UpdateImgUtil(BaseActivity activity, ImageView view) {
        this.activity = activity;
        this.view = view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case REQUEST_CODE_CAPTURE:
                //通过这种方式得到的图片经过压缩，分辨率非常低
                if (data == null) {
                    return;
                } else {
                    Bundle cameraBundle = data.getExtras();
                    if (cameraBundle != null) {
                        Bitmap cameraBitmap = cameraBundle.getParcelable("data");
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            file = new File(Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/", new Date().getTime() + ".png");
                        } else {
                            Toast.makeText(activity, R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
                            file = new File(Environment.getDownloadCacheDirectory() + "/DCIM/Camera/", new Date().getTime() + ".png");
                        }
                        try {
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                            cameraBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                            bos.flush();
                            bos.close();
                            Uri uri = null;
                            if (Build.VERSION.SDK_INT < 24) {
                                uri = Uri.fromFile(file);
                            } else {
                                uri = getImageContentUri(file);
                            }
                            //裁剪图片
                            startPhotoZoom(uri);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            default:
                break;
        }
    }

    /**
     * SDK24之后获取文件Uri的方式
     *
     * @param
     */
    private Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = activity.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return activity.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 裁剪图片文件
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, REQUEST_CODE_CUTTING);
    }

    /**
     * 将裁剪后的图片设置到控件上并且上传图片到服务器
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (photo != null)
                photo.compress(Bitmap.CompressFormat.PNG, 100, baos);

//            if (view instanceof SimpleDraweeView) {
            Glide.with(activity)
                    .load(baos.toByteArray())
                    .into(view);
//            } else {
//                Drawable drawable = new BitmapDrawable(activity.getResources(), photo);
//                view.setImageDrawable(drawable);
//            }
            try {
                File uploadFile = bytesFile(baos.toByteArray());
                activity.updateImg(uploadFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                            .onGranted(permissions -> {
                                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                                    file = new File(Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/", new Date().getTime() + ".png");
//                                } else {
//                                    Toast.makeText(activity, R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
//                                    file = new File(Environment.getDownloadCacheDirectory(), new Date().getTime() + ".png");
//                                }
//                                if (!file.exists()) {
//                                    file.mkdirs();
//                                }
//                                if (Build.VERSION.SDK_INT < 24) {
//                                    uri = Uri.fromFile(file);
//                                } else {
//                                    uri = getImageContentUri(file);
//                                }
                                //华为P10系统8.0手机通过这种方式保存不了原图
//                                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                activity.startActivityForResult(captureIntent, REQUEST_CODE_CAPTURE);
                            }).start();
                    dialog.dismiss();
                }
        );
        dialog.findViewById(R.id.tv_pick_photo).setOnClickListener(view1 -> {
                    AndPermission.with(activity)
                            .permission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            .onGranted(permissions -> {
                                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                activity.startActivityForResult(pickIntent, REQUEST_CODE_PICK);
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
