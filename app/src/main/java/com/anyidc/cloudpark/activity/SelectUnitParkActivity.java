package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

/**
 * Created by Administrator on 2018/4/3.
 */

public class SelectUnitParkActivity extends BaseActivity {

    private String parkId;
    private ImageView ivRight;

    public static void actionStart(Context context, String parkId) {
        Intent intent = new Intent(context, SelectUnitParkActivity.class);
        intent.putExtra("parkId", parkId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_unit_park;
    }

    @Override
    protected void initData() {
        initTitle("选择车位");
        ivRight = findViewById(R.id.iv_right);
        ivRight.setImageResource(R.mipmap.img_park_list);
        ivRight.setVisibility(View.VISIBLE);
        parkId = getIntent().getStringExtra("parkId");
        getParkList();
    }

    private void getParkList() {
        getTime(Api.getDefaultService().getParkList(parkId)
                , new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {

                    }
                });
    }
}
