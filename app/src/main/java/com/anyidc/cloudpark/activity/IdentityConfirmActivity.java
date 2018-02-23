package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Administrator on 2018/2/22.
 */

public class IdentityConfirmActivity extends BaseActivity implements View.OnClickListener {
    private EditText etRealName;
    private EditText etIdNum;
    private ImageView ivIdPos;
    private ImageView ivIdNeg;
    private Button btnNext;
    private UploadImageUtil imgUtil;
    private String idPosImgUrl;
    private String idNegImgUrl;
    private final int POS = 1;
    private final int NEG = 2;
    private int which;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_identity_confirm;
    }

    @Override
    protected void initData() {
        initTitle("身份认证");
        etRealName = findViewById(R.id.et_real_name);
        etIdNum = findViewById(R.id.et_id_num);
        ivIdPos = findViewById(R.id.iv_id_pos);
        ivIdPos.setOnClickListener(this);
        ivIdNeg = findViewById(R.id.iv_id_neg);
        ivIdNeg.setOnClickListener(this);
        btnNext = findViewById(R.id.btn_next_step);
        btnNext.setOnClickListener(this);
        findViewById(R.id.tv_skip).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.iv_id_pos:
                which = POS;
                imgUtil = new UploadImageUtil(this, ivIdPos);
                imgUtil.uploadHeadPhoto();
                break;
            case R.id.iv_id_neg:
                which = NEG;
                imgUtil = new UploadImageUtil(this, ivIdNeg);
                imgUtil.uploadHeadPhoto();
                break;
            case R.id.btn_next_step:
                idConfirm();
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
                                idPosImgUrl = url;
                                break;
                            case NEG:
                                idNegImgUrl = url;
                                break;
                        }
                    }
                });
    }

    private void idConfirm() {
        String realName = etRealName.getText().toString();
        String idNum = etIdNum.getText().toString();
        if (TextUtils.isEmpty(idPosImgUrl)) {
            Toast.makeText(this, "您还未上传手持身份证正面照", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(idPosImgUrl)) {
            Toast.makeText(this, "您还未上传手持身份证背面照", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(realName)) {
            Toast.makeText(this, "真实姓名格式错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(idNum)) {
            Toast.makeText(this, "身份证号码格式错误", Toast.LENGTH_SHORT).show();
            return;
        }
        getTime(Api.getDefaultService().idConfirm(realName, idPosImgUrl, idNegImgUrl, idNum),
                new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        startActivity(new Intent(IdentityConfirmActivity.this, AddCarActivity.class));
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
