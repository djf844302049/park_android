package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anyidc.cloudpark.BaseApplication;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.utils.ViewUtils;

/**
 * 管理员界面
 * Created by Administrator on 2018/3/14.
 */

public class ManagerActivity extends BaseActivity implements View.OnClickListener{
    private ImageView ivScan,ivUpDown;

    public static void start(Context context){
        Intent intent = new Intent(context,ManagerActivity.class);
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
        ViewGroup.LayoutParams lp = ivScan.getLayoutParams();
        lp.width = ViewUtils.getWindowWidth(this) - ViewUtils.dip2px(this,34);
        lp.height = lp.width * 509 / 1040;
        ivScan.setLayoutParams(lp);

        ViewGroup.LayoutParams lp1 = ivUpDown.getLayoutParams();
        lp1.width = ViewUtils.getWindowWidth(this) - ViewUtils.dip2px(this,34);
        lp1.height = lp1.width * 509 / 1040;
        ivUpDown.setLayoutParams(lp1);

        ivScan.setOnClickListener(this);
        ivUpDown.setOnClickListener(this);
        initTitle("管理主界面");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            BaseApplication.getInstance().exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_scan:
                CarMonitorActivity.start(ManagerActivity.this,0);
                break;
            case R.id.iv_up_down:
                CarMonitorActivity.start(ManagerActivity.this,1);
                break;
        }
    }
}
