package com.anyidc.cloudpark.activity;

import android.view.View;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.UserInfoBean;
import com.anyidc.cloudpark.utils.SpUtils;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/2/24.
 */

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private CircleImageView ivAvatar;
    private TextView tvUserName;
    private TextView tvSex;

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
        UserInfoBean infoBean = SpUtils.getObject(SpUtils.USERINFO, UserInfoBean.class);
        if (infoBean != null) {
            Glide.with(this).load(infoBean.getHeader_img()).into(ivAvatar);
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
                break;
            case R.id.ll_user_name:
                break;
            case R.id.ll_sex:
                break;
        }
    }
}
