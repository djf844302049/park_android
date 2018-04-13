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

public class MyBankCardActivity extends BaseActivity {
    private RecyclerView rlvBankCard;
    private List<BankCardBean> list = new ArrayList<>();
    private MyBankCardAdapter adapter;
    private final int ADDBANKCARD = 999;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_bank_card;
    }

    @Override
    protected void initData() {
        initTitle("银行卡");
        rlvBankCard = findViewById(R.id.rlv_bank_card);
        findViewById(R.id.tv_add_bank).setOnClickListener(clickListener);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rlvBankCard.setLayoutManager(manager);
        rlvBankCard.setNestedScrollingEnabled(false);
        adapter = new MyBankCardAdapter(list);
        rlvBankCard.setAdapter(adapter);
        getBankCardList();
    }

    @Override
    public void onCheckDoubleClick(View v) {
        startActivityForResult(new Intent(this, BindBankCardActivity.class),ADDBANKCARD);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADDBANKCARD){
            if (resultCode==RESULT_OK){
                getBankCardList();
            }
        }
    }
}
