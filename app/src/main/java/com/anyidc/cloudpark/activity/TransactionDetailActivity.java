package com.anyidc.cloudpark.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.TransactionAdapter;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.TransactionBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */

public class TransactionDetailActivity extends BaseActivity {
    private XRefreshView xRefreshView;
    private RecyclerView xlvDetails;
    private TransactionAdapter adapter;
    private List<TransactionBean.ListBean> list = new ArrayList<>();
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
        adapter = new TransactionAdapter(list);
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
                new RxObserver<BaseEntity<TransactionBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<TransactionBean> baseEntity) {
                        TransactionBean data = baseEntity.getData();
                        if (page == 1) {
                            list.clear();
                            xRefreshView.stopRefresh();
                        } else {
                            xRefreshView.stopLoadMore();
                        }
                        page = data.getPage_num() + 1;
                        if (data.getTotal() < 10) {
                            xRefreshView.setPullLoadEnable(false);
                        }
                        List<TransactionBean.ListBean> beans = data.getList();
                        if (beans != null) {
                            list.addAll(beans);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String errMsg) {
                        super.onError(errMsg);
                        if (page == 1) {
                            xRefreshView.stopRefresh();
                        } else {
                            xRefreshView.stopLoadMore();
                        }
                    }
                });
    }
}
