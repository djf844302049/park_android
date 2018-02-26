package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.CenterBean;
import com.anyidc.cloudpark.moduel.InfoBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.LoginUtil;
import com.anyidc.cloudpark.utils.SpUtils;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/2/11.
 */

public class MineActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvBalance;
    private TextView tvIdconState;
    private CircleImageView ivAvatar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    protected void initData() {
        initTitle("我的");
        findViewById(R.id.ll_setting).setOnClickListener(this);
        findViewById(R.id.ll_usual_question).setOnClickListener(this);
        tvBalance = findViewById(R.id.tv_balance);
        tvIdconState = findViewById(R.id.tv_id_confirm_state);
        ivAvatar = findViewById(R.id.iv_avatar);
        if (LoginUtil.isLogin()) {
            ivAvatar.setOnClickListener(this);
        }
        getCenterData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        InfoBean infoBean = SpUtils.getObject(SpUtils.USERINFO, InfoBean.class);
        if (infoBean != null) {
            Glide.with(this).load(infoBean.getHeader_img()).placeholder(R.mipmap.ic_launcher).dontAnimate().into(ivAvatar);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.ll_usual_question:
                startActivity(new Intent(this, UsualQuestionActivity.class));
                break;
            case R.id.iv_avatar:
                startActivity(new Intent(this, UserInfoActivity.class));
                break;
        }
    }

    private void getCenterData() {
        getTime(Api.getDefaultService().center(),
                new RxObserver<BaseEntity<CenterBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<CenterBean> centerBean) {
                        tvBalance.setText("余额：￥" + centerBean.getData().getUser_money());
                        switch (centerBean.getData().getIsAuth().getIsAuth()) {
                            case 0:
                                tvIdconState.setText("认证失败");
                                break;
                            case 1:
                                tvIdconState.setText("已认证");
                                break;
                            case 2:
                                tvIdconState.setText("审核中");
                                break;
                        }
                    }
                });
    }
}
