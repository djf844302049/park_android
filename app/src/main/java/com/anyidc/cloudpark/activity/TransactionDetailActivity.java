package com.anyidc.cloudpark.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.TransactionAdapter;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

/**
 * Created by Administrator on 2018/3/12.
 */

public class TransactionDetailActivity extends BaseActivity {
    private XRefreshView xRefreshView;
    private RecyclerView xlvDetails;
    private TransactionAdapter adapter;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void initData() {
        initTitle("交易明细");
        xRefreshView = findViewById(R.id.my_xrefreshview);
        xlvDetails = findViewById(R.id.rlv_message_list);
        adapter = new TransactionAdapter(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        xlvDetails.setLayoutManager(manager);
        xlvDetails.setAdapter(adapter);
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                getTransactionDetails();
            }

            @Override
            public void onRefresh(boolean isPullDown) {
                page = 1;
                xRefreshView.setPullLoadEnable(true);
                getTransactionDetails();
            }
        });
        xRefreshView.startRefresh();
    }

    private void getTransactionDetails() {
        getTime(Api.getDefaultService().getPayList(page, 10),
                new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        if (page == 1) {
                            xRefreshView.stopRefresh();
                        } else {
                            xRefreshView.stopLoadMore();
                        }
                    }
                });
    }
}
