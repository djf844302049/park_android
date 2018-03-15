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
    private TextView tvIdConState;
    private CircleImageView ivAvatar;
    private ImageView ivRight;
    private TextView tvLogin;
    private TextView tvUserName;

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
        findViewById(R.id.tv_my_car).setOnClickListener(this);
        findViewById(R.id.tv_stop_record).setOnClickListener(this);
        findViewById(R.id.tv_car_monitor).setOnClickListener(this);
        findViewById(R.id.ll_my_wallet).setOnClickListener(this);
        tvLogin = findViewById(R.id.tv_login);
        tvLogin.setOnClickListener(this);
        tvUserName = findViewById(R.id.tv_user_name);
        tvBalance = findViewById(R.id.tv_balance);
        tvIdConState = findViewById(R.id.tv_id_confirm_state);
        ivAvatar = findViewById(R.id.iv_avatar);
        ivRight = findViewById(R.id.iv_right);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.img_mess);
        if (LoginUtil.isLogin()) {
            getCenterData();
        }
        ivRight.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginUtil.isLogin()) {
            Glide.with(this).load(CacheData.getHeader_img()).placeholder(R.mipmap.ic_launcher).dontAnimate().into(ivAvatar);
            ivAvatar.setOnClickListener(this);
            tvLogin.setVisibility(View.GONE);
            tvUserName.setVisibility(View.VISIBLE);
            tvUserName.setText(CacheData.getUserName());
        } else {
            ivAvatar.setImageResource(R.mipmap.ic_launcher);
            tvIdConState.setText("");
            ivAvatar.setOnClickListener(null);
            tvLogin.setVisibility(View.VISIBLE);
            tvUserName.setVisibility(View.GONE);
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
            case R.id.ll_id_confirm:
                if (LoginUtil.isLogin()) {
                    String state = tvIdConState.getText().toString();
                    if ("未认证".equals(state) || "认证失败".equals(state)) {
                        startActivityForResult(new Intent(this, IdentityConfirmActivity.class).putExtra("from", 0), 1);
                    }
                }
                break;
            case R.id.ll_advise:
                if (LoginUtil.isLogin())
                    startActivity(new Intent(this, AdviseActivity.class));
                break;
            case R.id.tv_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.iv_right:
                if (LoginUtil.isLogin())
                    startActivity(new Intent(this, MessageCenterActivity.class));
                break;
            case R.id.tv_my_car:
                if (LoginUtil.isLogin())
                    startActivity(new Intent(this, MyCarActivity.class));
                break;
            case R.id.tv_stop_record:
                if (LoginUtil.isLogin())
                    startActivity(new Intent(this, StopRecordActivity.class));
                break;
            case R.id.tv_car_monitor:
                startActivity(new Intent(this, CarMonitorActivity.class));
                break;
            case R.id.ll_my_wallet:
                if (LoginUtil.isLogin())
                    startActivity(new Intent(this, PurseActivity.class));
                break;
        }
    }

    private void getCenterData() {
        getTime(Api.getDefaultService().center(),
                new RxObserver<BaseEntity<CenterBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<CenterBean> centerBean) {
                        tvBalance.setText("余额：￥" + centerBean.getData().getUser_money());
                        switch (centerBean.getData().getIsAuth()) {
                            case 0:
                                tvIdConState.setText("认证失败");
                                break;
                            case 1:
                                tvIdConState.setText("已认证");
                                break;
                            case 2:
                                tvIdConState.setText("审核中");
                                break;
                            case 3:
                                tvIdConState.setText("未认证");
                                break;
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            tvIdConState.setText("审核中");
        }
    }
}
