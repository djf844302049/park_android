package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.utils.LoginUtil;
import com.anyidc.cloudpark.utils.ViewUtils;

/**
 * 管理员界面
 * Created by Administrator on 2018/3/14.
 */

public class ManagerActivity extends BaseActivity {
    private ImageView ivScan, ivUpDown, ivRight;

    public static void start(Context context) {
        Intent intent = new Intent(context, ManagerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manager;
    }

    @Override
    protected void initData() {
        ivScan = findViewById(R.id.iv_scan);
        ivUpDown = findViewById(R.id.iv_up_down);
        ivRight = findViewById(R.id.iv_right);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.img_mess);
        ivRight.setOnClickListener(clickListener);
        ViewGroup.LayoutParams lp = ivScan.getLayoutParams();
        lp.width = ViewUtils.getWindowWidth(this) - ViewUtils.dip2px(this, 34);
        lp.height = lp.width * 509 / 1040;
        ivScan.setLayoutParams(lp);

        ViewGroup.LayoutParams lp1 = ivUpDown.getLayoutParams();
        lp1.width = ViewUtils.getWindowWidth(this) - ViewUtils.dip2px(this, 34);
        lp1.height = lp1.width * 509 / 1040;
        ivUpDown.setLayoutParams(lp1);

        ivScan.setOnClickListener(clickListener);
        ivUpDown.setOnClickListener(clickListener);
        findViewById(R.id.iv_back).setOnClickListener(v -> {
            startActivity(new Intent(ManagerActivity.this, MainActivity.class));
            LoginUtil.logout();
            finish();
        });
        initTitle("管理主界面");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(ManagerActivity.this, MainActivity.class));
            LoginUtil.logout();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.iv_scan:
                CarMonitorActivity.start(ManagerActivity.this, 0);
                break;
            case R.id.iv_up_down:
                CarMonitorActivity.start(ManagerActivity.this, 1);
                break;
            case R.id.iv_right:
                startActivity(new Intent(this, MessageCenterActivity.class));
                break;
        }
    }
}
