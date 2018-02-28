package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.UpdateImgBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.UploadImageUtil;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/2/27.
 */

public class AdviseActivity extends BaseActivity implements View.OnClickListener {
    private ScrollView svAdvise;
    private LinearLayout llAdviseComplete;
    private ImageView ivPic1;
    private ImageView ivPic2;
    private ImageView ivPic3;
    private EditText etQuestion;
    private ImageView ivComplete;
    private TextView tvComplete;
    private TextView tvCompleteTip;
    private int which;
    private Gson gson;
    private Map<String, String> urls;
    private UploadImageUtil imageUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_advise;
    }

    @Override
    protected void initData() {
        initTitle("意见反馈");
        gson = new Gson();
        urls = new HashMap<>();
        svAdvise = findViewById(R.id.sv_advise);
        llAdviseComplete = findViewById(R.id.ll_advise_complete);
        etQuestion = findViewById(R.id.et_question);
        ivComplete = findViewById(R.id.iv_complete);
        ivComplete.setImageResource(R.mipmap.img_bold_tick);
        tvComplete = findViewById(R.id.tv_complete);
        tvComplete.setText(R.string.question_commit_success);
        tvCompleteTip = findViewById(R.id.tv_complete_tip);
        tvCompleteTip.setText(R.string.question_commit_success_tip);
        ivPic1 = findViewById(R.id.iv_pic_1);
        ivPic1.setOnClickListener(this);
        ivPic2 = findViewById(R.id.iv_pic_2);
        ivPic2.setOnClickListener(this);
        ivPic3 = findViewById(R.id.iv_pic_3);
        ivPic3.setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_pic_1:
                which = 1;
                imageUtil = new UploadImageUtil(this, ivPic1);
                imageUtil.uploadHeadPhoto();
                break;
            case R.id.iv_pic_2:
                which = 2;
                imageUtil = new UploadImageUtil(this, ivPic2);
                imageUtil.uploadHeadPhoto();
                break;
            case R.id.iv_pic_3:
                which = 3;
                imageUtil = new UploadImageUtil(this, ivPic3);
                imageUtil.uploadHeadPhoto();
                break;
            case R.id.btn_commit:
                commitQuestion();
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
                        String imgUrl = updateImgBean.getData().getUrl();
                        switch (which) {
                            case 1:
                                urls.put("0", imgUrl);
                                ivPic2.setVisibility(View.VISIBLE);
                                break;
                            case 2:
                                urls.put("1", imgUrl);
                                ivPic3.setVisibility(View.VISIBLE);
                                break;
                            case 3:
                                urls.put("2", imgUrl);
                                break;
                        }
                    }
                });
    }

    private void commitQuestion() {
        String question = etQuestion.getText().toString();
        String imgUrls = gson.toJson(urls);
        if (TextUtils.isEmpty(question) && urls.size() == 0) {
            return;
        }
        getTime(Api.getDefaultService().addAdvise(question, imgUrls)
                , new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        svAdvise.setVisibility(View.GONE);
                        llAdviseComplete.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUtil.onActivityResult(requestCode, resultCode, data);
    }
}
