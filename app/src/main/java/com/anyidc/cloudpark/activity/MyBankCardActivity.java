package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.MyBankCardAdapter;
import com.anyidc.cloudpark.moduel.BankCardBean;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class MyBankCardActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rlvBankCard;
    private List<BankCardBean> list = new ArrayList<>();
    private MyBankCardAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_bank_card;
    }

    @Override
    protected void initData() {
        initTitle("银行卡");
        rlvBankCard = findViewById(R.id.rlv_bank_card);
        findViewById(R.id.tv_add_bank).setOnClickListener(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rlvBankCard.setLayoutManager(manager);
        rlvBankCard.setNestedScrollingEnabled(false);
        adapter = new MyBankCardAdapter(list);
        rlvBankCard.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBankCardList();
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, BindBankCardActivity.class));
    }

    public void getBankCardList() {
        getTime(Api.getDefaultService().getBankCard(),
                new RxObserver<BaseEntity<List<BankCardBean>>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<List<BankCardBean>> baseEntity) {
                        list.clear();
                        list.addAll(baseEntity.getData());
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
