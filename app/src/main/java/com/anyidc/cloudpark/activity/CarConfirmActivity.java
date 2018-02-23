package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.UpdateImgBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.UploadImageUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/2/23.
 */

public class CarConfirmActivity extends BaseActivity implements View.OnClickListener {

    private ImageView icLicensePos;
    private ImageView icLicenseNeg;
    private Button btnConfirm;
    private String licensePosImgUrl;
    private String licenseNegImgUrl;
    private UploadImageUtil imgUtil;
    private String id;
    private final int POS = 1;
    private final int NEG = 2;
    private int which;

    public static void actionStart(Context context, String id) {
        Intent intent = new Intent(context, CarConfirmActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_confirm;
    }

    @Override
    protected void initData() {
        icLicensePos = findViewById(R.id.iv_license_pos);
        icLicensePos.setOnClickListener(this);
        icLicenseNeg = findViewById(R.id.iv_license_neg);
        icLicenseNeg.setOnClickListener(this);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(this);
        findViewById(R.id.tv_skip).setOnClickListener(this);
        id = getIntent().getStringExtra("id");
        id = "6";
        initTitle("车辆认证");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.iv_license_pos:
                which = POS;
                imgUtil = new UploadImageUtil(this, icLicensePos);
                imgUtil.uploadHeadPhoto();
                break;
            case R.id.iv_license_neg:
                which = NEG;
                imgUtil = new UploadImageUtil(this, icLicenseNeg);
                imgUtil.uploadHeadPhoto();
                break;
            case R.id.btn_confirm:
                carAuth();
                break;
        }
    }

    @Override
    public void updateImg(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        getTime(Api.getDefaultService().uploadImg(imageBodyPart)
                , new RxObserver<BaseEntity<UpdateImgBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<UpdateImgBean> updateImgBean) {
                        String url = updateImgBean.getData().getUrl();
                        switch (which) {
                            case POS:
                                licensePosImgUrl = url;
                                break;
                            case NEG:
                                licenseNegImgUrl = url;
                                break;
                        }
                    }
                });
    }

    private void carAuth() {
        if (TextUtils.isEmpty(licensePosImgUrl)) {
            Toast.makeText(this, "您还未上传行驶证主页", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(licenseNegImgUrl)) {
            Toast.makeText(this, "您还未上传行驶证副页", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(id)) {
            Toast.makeText(this, "要认证的车辆id异常", Toast.LENGTH_SHORT).show();
            return;
        }
        getTime(Api.getDefaultService().carAuth(id, licensePosImgUrl, licenseNegImgUrl)
                , new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        startActivity(new Intent(CarConfirmActivity.this, MainActivity.class));
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (imgUtil != null) {
            imgUtil.onActivityResult(requestCode, resultCode, data);
        }
    }
}
