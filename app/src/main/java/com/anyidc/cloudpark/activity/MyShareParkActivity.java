package com.anyidc.cloudpark.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.MyShareAdapter;

/**
 * Created by Administrator on 2018/3/18.
 */

public class MyShareParkActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private MyShareAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_share_park;
    }

    @Override
    protected void initData() {
        initTitle("我的共享车位");
        recyclerView = findViewById(R.id.rlv_my_share);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new MyShareAdapter(this);
        recyclerView.setAdapter(adapter);
    }
}
