package com.anyidc.cloudpark.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.MessageAdapter;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.MessageBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.SpUtils;
import com.anyidc.cloudpark.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/28.
 */

public class MessageCenterActivity extends BaseActivity {
    private XRefreshView xRefreshView;
    private MessageAdapter adapter;
    private List<MessageBean.OrderBean> messages = new ArrayList<>();
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void initData() {
        initTitle("消息中心");
        xRefreshView = findViewById(R.id.my_xrefreshview);
        RecyclerView recyclerView = findViewById(R.id.rlv_message_list);
        TextView tvRight = findViewById(R.id.tv_right);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("清空");
        tvRight.setOnClickListener(clickListener);
        adapter = new MessageAdapter(messages);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        initXRefreshView();
        xRefreshView.startRefresh();
    }

    private void initXRefreshView() {
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                page = 1;
                xRefreshView.setPullLoadEnable(true);
                getMessages();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                getMessages();
            }
        });
    }

    @Override
    public void onCheckDoubleClick(View v) {
        if (messages.size() > 0) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("确定清空全部消息吗？")
                    .setPositiveButton("确定", (dialog, which) -> deleteMessages())
                    .setNegativeButton("取消", null)
                    .show();
        }
    }

    private void getMessages() {
        getTime(Api.getDefaultService().getMessages(page, 10)
                , new RxObserver<BaseEntity<MessageBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<MessageBean> messageBean) {
                        MessageBean data = messageBean.getData();
                        if (page == 1) {
                            xRefreshView.stopRefresh();
                            messages.clear();
                        } else {
                            xRefreshView.stopLoadMore();
                        }
                        page++;
                        List<MessageBean.OrderBean> order = data.getOrder();
                        if (order != null) {
                            messages.addAll(order);
                            if (order.size() < 10) {
                                xRefreshView.setPullLoadEnable(false);
                            }
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

    private void deleteMessages() {
        getTime(Api.getDefaultService().deleteMessages(1)
                , new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        messages.clear();
                        adapter.notifyDataSetChanged();
                        ToastUtil.showToast(baseEntity.getMessage(), Toast.LENGTH_SHORT);
                    }
                });
    }

}
