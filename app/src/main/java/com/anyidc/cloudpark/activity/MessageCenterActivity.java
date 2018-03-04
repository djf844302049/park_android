package com.anyidc.cloudpark.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.andview.refreshview.XRefreshView;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.MessageAdapter;

/**
 * Created by Administrator on 2018/2/28.
 */

public class MessageCenterActivity extends BaseActivity {
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void initData() {
        initTitle("消息中心");
        xRefreshView = findViewById(R.id.my_xrefreshview);
        recyclerView = findViewById(R.id.rlv_message_list);
        adapter = new MessageAdapter(this);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        initXRefreshView();
    }

    private void initXRefreshView(){
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setPullRefreshEnable(true);
        xRefreshView.setAutoLoadMore(false);
        xRefreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onRefresh(boolean isPullDown) {

            }

            @Override
            public void onLoadMore(boolean isSilence) {

            }

            @Override
            public void onRelease(float direction) {

            }

            @Override
            public void onHeaderMove(double headerMovePercent, int offsetY) {

            }
        });
    }


}
