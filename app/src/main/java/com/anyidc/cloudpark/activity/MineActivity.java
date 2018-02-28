package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.CenterBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.CacheData;
import com.anyidc.cloudpark.utils.LoginUtil;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/2/11.
 */

public class MineActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvBalance;
    private TextView tvIdconState;
    private CircleImageView ivAvatar;
    private ImageView ivRight;
    private TextView tvLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    protected void initData() {
        initTitle("我的");
        findViewById(R.id.ll_setting).setOnClickListener(this);
        findViewById(R.id.ll_usual_question).setOnClickListener(this);
        findViewById(R.id.ll_id_confirm).setOnClickListener(this);
        findViewById(R.id.ll_advise).setOnClickListener(this);
        tvLogin = findViewById(R.id.tv_login);
        tvLogin.setOnClickListener(this);
        tvBalance = findViewById(R.id.tv_balance);
        tvIdconState = findViewById(R.id.tv_id_confirm_state);
        ivAvatar = findViewById(R.id.iv_avatar);
        ivRight = findViewById(R.id.iv_right);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.img_mess);
        if (LoginUtil.isLogin()) {
            ivAvatar.setOnClickListener(this);
        }
        getCenterData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this).load(CacheData.getHeader_img()).placeholder(R.mipmap.ic_launcher).dontAnimate().into(ivAvatar);
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
            case R.id.ll_id_confirm:
                String state = tvIdconState.getText().toString();
                if ("未认证".equals(state) || "认证失败".equals(state)) {
                    startActivityForResult(new Intent(this, IdentityConfirmActivity.class).putExtra("from", 0), 1);
                }
                break;
            case R.id.ll_advise:
                startActivity(new Intent(this, AdviseActivity.class));
                break;
            case R.id.tv_login:
                startActivity(new Intent(this, LoginActivity.class));
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
                            case 3:
                                tvIdconState.setText("未认证");
                                break;
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            tvIdconState.setText("审核中");
        }
    }
}
