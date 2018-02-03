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
import android.widget.ImageView;

import com.anyidc.cloudpark.activity.BaseActivity;
import com.bumptech.glide.Glide;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class UpdateImgUtil {

    private static final int REQUEST_CODE_PICK = 1;
    private static final int REQUEST_CODE_CAPTURE = 3;
    private static final int REQUEST_CODE_CUTTING = 2;
    private File file;
    private BaseActivity activity;
    private ImageView view;

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
                if (file != null && file.length() > 0) {
                    Uri uri = null;
                    if (Build.VERSION.SDK_INT < 24) {
                        uri = Uri.fromFile(file);
                    } else {
                        uri = getImageContentUri(file);
                    }
                    startPhotoZoom(uri);
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
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
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
//        AlertDialog.Builder builder = null;
//        if (Build.VERSION.SDK_INT > 21) {
//            builder = new AlertDialog.Builder(activity);
//        } else {
//            builder = new AlertDialog.Builder(activity);
//        }
//        builder.setTitle(R.string.dl_title_upload_photo);
//        builder.setItems(new String[]{activity.getString(R.string.dl_msg_take_photo), activity.getString(R.string.dl_msg_local_upload)},
//                (dialog1, which) -> {
//                    dialog1.dismiss();
//                    switch (which) {
//                        case 0:
//                            if (Build.VERSION.SDK_INT >= 23) {
//                                int checkCallPhonePermission = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA);
//                                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
//                                    AlertDialog alertDialog = new AlertDialog.Builder(activity)
//                                            .setMessage("很遗憾你把相机权限禁用了，请到授权管理中打开该权限。")
//                                            .setCancelable(false)
//                                            .setPositiveButton("立即前往", (dialog, which1) -> {
//                                                Intent intent = new Intent(Settings.ACTION_SETTINGS);
//                                                activity.startActivity(intent);
//                                            }).setNegativeButton("稍后再说", null).create();
//                                    alertDialog.show();
//                                    return;
//                                }
//                            }
//                            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                                file = new File(Environment.getExternalStorageDirectory().getPath(), new Date().getTime() + ".png");
//                            } else {
//                                Toast.makeText(activity, R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
//                                file = new File(Environment.getDownloadCacheDirectory(), new Date().getTime() + ".png");
//                            }
//                            if (Build.VERSION.SDK_INT < 24) {
//                                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//                            } else {
//                                Uri uri = getImageContentUri(file);
//                                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                            }
//                            if (captureIntent.resolveActivity(activity.getPackageManager()) != null) {
//                                activity.startActivityForResult(captureIntent, REQUEST_CODE_CAPTURE);
//                            }
//                            break;
//                        case 1:
//                            if (Build.VERSION.SDK_INT >= 23) {
//                                int checkCallPhonePermission = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                                int checkCallPhonePermission1 = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE);
//                                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED || checkCallPhonePermission1 != PackageManager.PERMISSION_GRANTED) {
//                                    AlertDialog alertDialog = new AlertDialog.Builder(activity)
//                                            .setMessage("很遗憾您禁用了读写权限，请到授权管理中打开该权限。")
//                                            .setCancelable(false)
//                                            .setPositiveButton("立即前往", (dialog, which1) -> {
//                                                Intent intent = new Intent(Settings.ACTION_SETTINGS);
//                                                activity.startActivity(intent);
//                                            }).setNegativeButton("稍后再说", null).create();
//                                    alertDialog.show();
//                                    return;
//                                }
//                            }
//                            Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
//                            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                            activity.startActivityForResult(pickIntent, REQUEST_CODE_PICK);
//                            break;
//                        default:
//                            break;
//                    }
//                });
//        builder.create().show();
    }

    private File bytesFile(byte[] bytes) throws IOException {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory().getPath(), "uploadimg.JPG");
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
