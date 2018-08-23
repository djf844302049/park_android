package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.UpdateImgBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.ToastUtil;
import com.anyidc.cloudpark.utils.UploadImageUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/2/23.
 */

public class CarConfirmActivity extends BaseActivity {

    private ImageView icLicensePos;
    private ImageView icLicenseNeg;
    private String licensePosImgUrl;
    private String licenseNegImgUrl;
    private UploadImageUtil imgUtil;
    private ScrollView svCarConfirm;
    private LinearLayout llComplete;
    private String id;
    private final int POS = 1;
    private final int NEG = 2;
    private int which;

    public static void actionStart(Context context, String id, int from) {
        Intent intent = new Intent(context, CarConfirmActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_confirm;
    }

    @Override
    protected void initData() {
        icLicensePos = findViewById(R.id.iv_license_pos);
        icLicensePos.setOnClickListener(clickListener);
        icLicenseNeg = findViewById(R.id.iv_license_neg);
        icLicenseNeg.setOnClickListener(clickListener);
        Button btnConfirm = findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(clickListener);
        svCarConfirm = findViewById(R.id.sv_car_confirm);
        llComplete = findViewById(R.id.ll_commit_complete);
        ImageView ivComplete = findViewById(R.id.iv_complete);
        ivComplete.setImageResource(R.mipmap.img_time);
        TextView tvComplete = findViewById(R.id.tv_complete);
        tvComplete.setText("正在提交审核");
        TextView tvCompleteTip = findViewById(R.id.tv_complete_tip);
        tvCompleteTip.setText("您的信息已提交，请耐心等待");
        TextView tvSkip = findViewById(R.id.tv_skip);
        tvSkip.setOnClickListener(clickListener);
        id = getIntent().getStringExtra("id");
        int from = getIntent().getIntExtra("from", 0);
        switch (from) {
            case 1:
                break;
            default:
                tvSkip.setVisibility(View.GONE);
                break;
        }
        initTitle("车辆认证");
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
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

                    @Override
                    public void onError(String errMsg) {
                        super.onError(errMsg);
                        switch (which) {
                            case POS:
                                icLicensePos.setBackgroundResource(R.mipmap.img_upload);
                                break;
                            case NEG:
                                icLicenseNeg.setBackgroundResource(R.mipmap.img_upload);
                                break;
                        }
                    }
                });
    }

    private void carAuth() {
        if (TextUtils.isEmpty(licensePosImgUrl)) {
            ToastUtil.showToast("您还未上传行驶证主页", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(licenseNegImgUrl)) {
            ToastUtil.showToast("您还未上传行驶证副页", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(id)) {
            ToastUtil.showToast("要认证的车辆id异常", Toast.LENGTH_SHORT);
            return;
        }
        getTime(Api.getDefaultService().carAuth(id, licensePosImgUrl, licenseNegImgUrl)
                , new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        svCarConfirm.setVisibility(View.GONE);
                        llComplete.setVisibility(View.VISIBLE);
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
