package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.InfoBean;
import com.anyidc.cloudpark.moduel.UpdateImgBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.CacheData;
import com.anyidc.cloudpark.utils.UploadImageUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/2/22.
 */

public class CompleteBaseInfoActivity extends BaseActivity implements OnClickListener {
    private ImageView ivAvatar;
    private EditText etUserName;
    private RadioGroup rbGender;
    private Button btnNext;
    private UploadImageUtil imgUtil;
    private String imgUrl;
    private int sex;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_base_info;
    }

    @Override
    protected void initData() {
        initTitle("基本信息");
        findViewById(R.id.tv_skip).setOnClickListener(this);
        ivAvatar = findViewById(R.id.iv_upload_img);
        ivAvatar.setOnClickListener(this);
        etUserName = findViewById(R.id.et_user_name);
        rbGender = findViewById(R.id.rg_gender);
        rbGender.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.rb_male:
                    sex = 1;
                    break;
                case R.id.rb_female:
                    sex = 2;
                    break;
            }
        });
        btnNext = findViewById(R.id.btn_next_step);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btn_next_step:
                updateInfo();
                break;
            case R.id.iv_upload_img:
                if (imgUtil == null) {
                    imgUtil = new UploadImageUtil(this, ivAvatar);
                }
                imgUtil.uploadHeadPhoto();
                break;
        }
    }

    private void updateInfo() {
        String userName = etUserName.getText().toString();
        if (TextUtils.isEmpty(imgUrl)) {
            Toast.makeText(this, "您还未上传头像", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sex == 0) {
            Toast.makeText(this, "您还未选择性别", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "用户名格式错误", Toast.LENGTH_SHORT).show();
            return;
        }
        getTime(Api.getDefaultService().updateInfo(imgUrl, sex, userName)
                , new RxObserver<BaseEntity<InfoBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<InfoBean> infoBean) {
                        CacheData.setInfoBean(infoBean.getData());
                        IdentityConfirmActivity.actionStart(CompleteBaseInfoActivity.this, 1);
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgUtil.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void updateImg(File file) {
        super.updateImg(file);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        getTime(Api.getDefaultService().uploadImg(imageBodyPart)
                , new RxObserver<BaseEntity<UpdateImgBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<UpdateImgBean> updateImgBean) {
                        imgUrl = updateImgBean.getData().getUrl();
                    }
                });
    }
}
