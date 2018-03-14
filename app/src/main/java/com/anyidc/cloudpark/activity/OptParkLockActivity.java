package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.anyidc.cloudpark.R;

/**
 * 操作车位锁的界面
 * Created by Administrator on 2018/3/14.
 */

public class OptParkLockActivity extends BaseActivity implements View.OnClickListener{
    private ImageView ivUp,ivDown;

    public static void start(Context context){
        Intent intent = new Intent(context,OptParkLockActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_opt_park_lock;
    }

    @Override
    protected void initData() {
        ivUp = findViewById(R.id.iv_up);
        ivDown = findViewById(R.id.iv_down);
        initTitle("操作车位锁");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_up:
                break;
            case R.id.iv_down:
                break;
        }
    }
}
