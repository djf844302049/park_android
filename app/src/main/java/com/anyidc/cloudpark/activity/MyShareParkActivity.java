package com.anyidc.cloudpark.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.MyShareAdapter;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.MyShareBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/18.
 */

public class MyShareParkActivity extends BaseActivity {
    private MyShareAdapter adapter;
    private XRefreshView xRefreshView;
    private TextView tvTip;
    private ArrayList<MyShareBean.ShareParkBean> parkBeans = new ArrayList<>();
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_share_park;
    }

    @Override
    protected void initData() {
        initTitle("我的共享车位");
        tvTip = findViewById(R.id.tv_tip);
        xRefreshView = findViewById(R.id.xrv_share_park);
        RecyclerView recyclerView = findViewById(R.id.rlv_my_share);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new MyShareAdapter(this);
        recyclerView.setAdapter(adapter);
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                page = 1;
                xRefreshView.setPullLoadEnable(true);
                getMySharePark();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                getMySharePark();
            }
        });
        xRefreshView.startRefresh();
    }

    private void getMySharePark() {
        int size = 10;
        getTime(Api.getDefaultService().getMyshare(page, size)
                , new RxObserver<BaseEntity<MyShareBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<MyShareBean> shareBean) {
                        if (page == 1) {
                            xRefreshView.stopRefresh();
                            parkBeans.clear();
                        } else {
                            xRefreshView.stopLoadMore();
                        }
                        page++;
                        List<MyShareBean.ShareParkBean> list = shareBean.getData().getList();
                        if (list != null) {
                            parkBeans.addAll(list);
                            adapter.updateList(parkBeans);
                            adapter.notifyDataSetChanged();
                            if (list.size()<10){
                                xRefreshView.setPullLoadEnable(false);
                            }
                        }
                        if (parkBeans.size() == 0) {
                            tvTip.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
