package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.InfoBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.CacheData;

/**
 * Created by Administrator on 2018/2/26.
 */

public class InfoEditActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvRight;
    private ImageView ivMaleCheck;
    private ImageView ivFemaleCheck;
    private LinearLayout llMale;
    private LinearLayout llFemale;
    private EditText etUserName;
    private int which;
    private String userName;
    private int sex;

    public static void actionStart(Context context, int setWhich) {
        Intent intent = new Intent(context, InfoEditActivity.class);
        intent.putExtra("which", setWhich);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info_set;
    }

    @Override
    protected void initData() {

        tvRight = findViewById(R.id.tv_right);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("完成");
        tvRight.setOnClickListener(this);
        llMale = findViewById(R.id.ll_male);
        llMale.setOnClickListener(this);
        llFemale = findViewById(R.id.ll_female);
        llFemale.setOnClickListener(this);
        etUserName = findViewById(R.id.et_user_name);
        ivMaleCheck = findViewById(R.id.iv_male_check);
        ivFemaleCheck = findViewById(R.id.iv_female_check);
        which = getIntent().getIntExtra("which", 0);
        switch (which) {
            case 1:
                initTitle("设置用户名");
                llMale.setVisibility(View.GONE);
                llFemale.setVisibility(View.GONE);
                etUserName.setText(CacheData.getUserName());
                break;
            default:
                initTitle("设置性别");
                etUserName.setVisibility(View.GONE);
                switch (CacheData.getSex()) {
                    case 1:
                        ivMaleCheck.setVisibility(View.VISIBLE);
                        sex = 1;
                        break;
                    case 2:
                        ivFemaleCheck.setVisibility(View.VISIBLE);
                        sex = 2;
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                switch (which) {
                    case 1:
                        userName = etUserName.getText().toString().trim();
                        if (CacheData.getUserName().equals(userName)) {
                            finish();
                            return;
                        }
                        updateInfo(null, userName);
                        break;
                    default:
                        if (CacheData.getSex() == sex) {
                            finish();
                            return;
                        }
                        updateInfo(sex, null);
                        break;
                }
                break;
            case R.id.ll_male:
                ivMaleCheck.setVisibility(View.VISIBLE);
                ivFemaleCheck.setVisibility(View.GONE);
                sex = 1;
                break;
            case R.id.ll_female:
                ivMaleCheck.setVisibility(View.GONE);
                ivFemaleCheck.setVisibility(View.VISIBLE);
                sex = 2;
                break;
        }
    }

    private void updateInfo(Integer sex, String userName) {
        getTime(Api.getDefaultService().updateInfo(null, sex, userName)
                , new RxObserver<BaseEntity<InfoBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<InfoBean> infoBean) {
                        CacheData.setInfoBean(infoBean.getData());
                        finish();
                    }
                });
    }
}
