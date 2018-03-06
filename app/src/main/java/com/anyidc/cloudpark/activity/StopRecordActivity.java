package com.anyidc.cloudpark.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.StopRecordAdapter;
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
    private TextView tvInPro;
    private TextView tvComplete;
    private RelativeLayout rlInpro;
    private TextView tvParkName;
    private TextView tvParkDate;
    private TextView tvParkTime;
    private TextView tvParkPrice;
    private TextView tvNoMoreData;
    private StopRecordAdapter adapter;
    private boolean isParking;
    private List<StopRecordBean.OrderBean> orderBeanList = new ArrayList<>();
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
        tvInPro = findViewById(R.id.tv_in_progress);
        tvComplete = findViewById(R.id.tv_off_the_stock);
        rlInpro = findViewById(R.id.rl_in_progress);
        tvParkName = findViewById(R.id.tv_park_name);
        tvParkDate = findViewById(R.id.tv_park_date);
        tvParkTime = findViewById(R.id.tv_park_time);
        tvParkPrice = findViewById(R.id.tv_park_price);
        tvNoMoreData = findViewById(R.id.tv_tip_no_more_data);
        adapter = new StopRecordAdapter(this, orderBeanList);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
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
//                tvInPro.setVisibility(View.GONE);
//                rlInpro.setVisibility(View.GONE);
//                tvComplete.setVisibility(View.GONE);
//                tvNoMoreData.setVisibility(View.GONE);
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
                        isParking = false;
                        if (page == 1) {
                            xRefreshView.stopRefresh();
                            orderBeanList.clear();
                        } else {
                            xRefreshView.stopLoadMore();
                        }
                        if (recordBean.getData().getTotal() < 10) {
                            tvNoMoreData.setVisibility(View.VISIBLE);
                            xRefreshView.setPullLoadEnable(false);
                        } else {
                            tvNoMoreData.setVisibility(View.GONE);
                            xRefreshView.setPullLoadEnable(true);
                        }
                        page = recordBean.getData().getPage_num() + 1;
                        List<StopRecordBean.OrderBean> order = recordBean.getData().getOrder();
                        for (StopRecordBean.OrderBean orderBean : order) {
                            //未支付
                            if (orderBean.getPay_status() == 0) {
                                isParking = true;
                                tvInPro.setVisibility(View.VISIBLE);
                                rlInpro.setVisibility(View.VISIBLE);
                                tvParkName.setText(orderBean.getParking_name());
                                tvParkDate.setText(orderBean.getCreate_time());
                                tvParkTime.setText(orderBean.getCreate_time());
                                tvParkPrice.setText("￥" + orderBean.getTotal_amount() + "(计费中)");
                            } else {
                                tvComplete.setVisibility(View.VISIBLE);
                                orderBeanList.add(orderBean);
                            }
                        }
                        if (orderBeanList.size() == 0) {
                            tvComplete.setVisibility(View.GONE);
                        }
                        if (!isParking) {
                            tvInPro.setVisibility(View.GONE);
                            rlInpro.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
