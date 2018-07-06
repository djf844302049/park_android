package com.anyidc.cloudpark.activity;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.StopRecordAdapter;
import com.anyidc.cloudpark.adapter.StopRecordIngAdapter;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.StopRecordBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/4.
 */

public class StopRecordActivity extends BaseActivity {
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;
    private RecyclerView rlvIng;
    private TextView tvInPro;
    private TextView tvComplete;
    private TextView tvNoMoreData;
    private StopRecordAdapter adapter;
    private StopRecordIngAdapter ingAdapter;
    private List<StopRecordBean.OrderBean> orderBeanList = new ArrayList<>();
    private List<StopRecordBean.OrderBean> ingList = new ArrayList<>();
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stop_record;
    }

    @Override
    protected void initData() {
        initTitle("停车记录");
        xRefreshView = findViewById(R.id.xrv_stop_record);
        recyclerView = findViewById(R.id.rlv_stop_record_list);
        rlvIng = findViewById(R.id.rlv_stop_record_ing_list);
        tvInPro = findViewById(R.id.tv_in_progress);
        tvComplete = findViewById(R.id.tv_off_the_stock);
        tvNoMoreData = findViewById(R.id.tv_tip_no_more_data);
        adapter = new StopRecordAdapter(orderBeanList);
        ingAdapter = new StopRecordIngAdapter(ingList);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        rlvIng.setNestedScrollingEnabled(false);
        LinearLayoutManager lm2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rlvIng.setLayoutManager(lm2);
        rlvIng.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) != 0) {
                    int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());
                    outRect.top = space;
                }
            }
        });
        rlvIng.setAdapter(ingAdapter);
        rlvIng.setNestedScrollingEnabled(false);
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                super.onLoadMore(isSilence);
                getStopRecord();
            }

            @Override
            public void onRefresh(boolean isPullDown) {
                super.onRefresh(isPullDown);
                page = 1;
                xRefreshView.setPullLoadEnable(true);
                getStopRecord();
            }
        });
        xRefreshView.startRefresh();
    }

    private void getStopRecord() {
        getTime(Api.getDefaultService().getParkRecord(page, 10)
                , new RxObserver<BaseEntity<StopRecordBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<StopRecordBean> recordBean) {
                        if (page == 1) {
                            xRefreshView.stopRefresh();
                            orderBeanList.clear();
                            ingList.clear();
                        } else {
                            xRefreshView.stopLoadMore();
                        }
                        page++;
                        List<StopRecordBean.OrderBean> order = recordBean.getData().getOrder();
                        if (order.size() < 10) {
                            tvNoMoreData.setVisibility(View.VISIBLE);
                            xRefreshView.setPullLoadEnable(false);
                        } else {
                            tvNoMoreData.setVisibility(View.GONE);
                            xRefreshView.setPullLoadEnable(true);
                        }
                        for (StopRecordBean.OrderBean orderBean : order) {
                            //未支付
                            if (orderBean.getPay_status() == 0) {
                                tvInPro.setVisibility(View.VISIBLE);
                                ingList.add(orderBean);
                            } else {
                                tvComplete.setVisibility(View.VISIBLE);
                                orderBeanList.add(orderBean);
                            }
                        }
                        if (ingList.size() == 0) {
                            tvInPro.setVisibility(View.GONE);
                        }
                        if (orderBeanList.size() == 0) {
                            tvComplete.setVisibility(View.GONE);
                        }
                        ingAdapter.notifyDataSetChanged();
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
