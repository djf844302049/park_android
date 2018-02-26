package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.InfoBean;
import com.anyidc.cloudpark.moduel.UpdateImgBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.SpUtils;
import com.anyidc.cloudpark.utils.UploadImageUtil;
import com.bumptech.glide.Glide;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/2/24.
 */

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private CircleImageView ivAvatar;
    private TextView tvUserName;
    private TextView tvSex;
    private UploadImageUtil imageUtil;
    private String imgUrl;
    private InfoBean infoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_info;
    }

    @Override
    protected void initData() {
        initTitle("编辑资料");
        ivAvatar = findViewById(R.id.iv_avatar);
        tvUserName = findViewById(R.id.tv_user_name);
        tvSex = findViewById(R.id.tv_sex);
        findViewById(R.id.ll_avatar).setOnClickListener(this);
        findViewById(R.id.ll_user_name).setOnClickListener(this);
        findViewById(R.id.ll_sex).setOnClickListener(this);
        infoBean = SpUtils.getObject(SpUtils.USERINFO, InfoBean.class);
        if (infoBean != null)
            Glide.with(this).load(infoBean.getHeader_img())
                    .placeholder(R.mipmap.ic_launcher).dontAnimate().into(ivAvatar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        infoBean = SpUtils.getObject(SpUtils.USERINFO, InfoBean.class);
        if (infoBean != null) {
            tvUserName.setText(infoBean.getUsername());
            switch (infoBean.getSex()) {
                case 1:
                    tvSex.setText("男");
                    break;
                case 2:
                    tvSex.setText("女");
                    break;
                default:
                    tvSex.setText("未设置");
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_avatar:
                if (imageUtil == null) {
                    imageUtil = new UploadImageUtil(this, ivAvatar);
                }
                imageUtil.uploadHeadPhoto();
                break;
            case R.id.ll_user_name:
                InfoEditActivity.actionStart(this, 1);
                break;
            case R.id.ll_sex:
                InfoEditActivity.actionStart(this, 2);
                break;
        }
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
                        updateUserInfo();
                    }
                });
    }

    private void updateUserInfo() {
        getTime(Api.getDefaultService().updateInfo(imgUrl, null, null)
                , new RxObserver<BaseEntity<InfoBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<InfoBean> infoBean) {
                        SpUtils.setObject(SpUtils.USERINFO, infoBean.getData());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUtil.onActivityResult(requestCode, resultCode, data);
    }
}
