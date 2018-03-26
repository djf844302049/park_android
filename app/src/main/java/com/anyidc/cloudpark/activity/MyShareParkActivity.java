package com.anyidc.cloudpark.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.MyShareAdapter;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.MyCarBean;
import com.anyidc.cloudpark.moduel.MyShareBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

import java.util.List;

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
        getMySharePark();
    }

    private void getMySharePark(){
        getTime(Api.getDefaultService().getMyshare()
                , new RxObserver<BaseEntity<MyShareBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<MyShareBean> shareBean) {
                        MyShareBean data = shareBean.getData();
                        if (data != null) {
                            adapter.updateList(data.getList());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
