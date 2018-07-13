package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
 * Created by Administrator on 2018/2/22.
 */

public class IdentityConfirmActivity extends BaseActivity {
    private EditText etRealName;
    private EditText etIdNum;
    private ImageView ivIdPos;
    private ImageView ivIdNeg;
    private TextView tvSkip;
    private Button btnNext;
    private UploadImageUtil imgUtil;
    private String idPosImgUrl;
    private String idNegImgUrl;
    private final int POS = 1;
    private final int NEG = 2;
    private int which;
    private int from;

    public static void actionStart(Context context, int from) {
        Intent intent = new Intent(context, IdentityConfirmActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

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
        ivIdPos.setOnClickListener(clickListener);
        ivIdNeg = findViewById(R.id.iv_id_neg);
        ivIdNeg.setOnClickListener(clickListener);
        btnNext = findViewById(R.id.btn_next_step);
        btnNext.setOnClickListener(clickListener);
        tvSkip = findViewById(R.id.tv_skip);
        from = getIntent().getIntExtra("from", 0);
        switch (from) {
            case 1:
                tvSkip.setOnClickListener(clickListener);
                break;
            default:
                btnNext.setText("提交");
                tvSkip.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
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

                    @Override
                    public void onError(String errMsg) {
                        super.onError(errMsg);
                        switch (which) {
                            case POS:
                                ivIdPos.setBackgroundResource(R.mipmap.img_upload);
                                break;
                            case NEG:
                                ivIdNeg.setBackgroundResource(R.mipmap.img_upload);
                                break;
                        }
                    }
                });
    }

    private void idConfirm() {
        String realName = etRealName.getText().toString();
        String idNum = etIdNum.getText().toString();
        if (TextUtils.isEmpty(idPosImgUrl)) {
            ToastUtil.showToast("您还未上传手持身份证正面照", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(idPosImgUrl)) {
            ToastUtil.showToast("您还未上传手持身份证背面照", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(realName)) {
            ToastUtil.showToast("真实姓名格式错误", Toast.LENGTH_SHORT);

            return;
        }
        if (TextUtils.isEmpty(idNum)) {
            ToastUtil.showToast("身份证号码格式错误", Toast.LENGTH_SHORT);
            return;
        }
        getTime(Api.getDefaultService().idConfirm(realName, idPosImgUrl, idNegImgUrl, idNum),
                new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        switch (from) {
                            case 1:
                                AddCarActivity.actionStart(IdentityConfirmActivity.this, 1);
                                break;
                            default:
                                setResult(RESULT_OK);
                                finish();
                                break;
                        }
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
