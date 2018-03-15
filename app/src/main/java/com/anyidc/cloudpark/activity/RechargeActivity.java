package com.anyidc.cloudpark.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.RechargeAdapter;
import com.anyidc.cloudpark.moduel.RechargeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */

public class RechargeActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rlv;
    private RechargeAdapter adapter;
    private List<RechargeBean> list = new ArrayList<>();
    private ImageView ivAlPay, ivWxPay;
    private int prePosition;
    private int rechargeNum;
    private int[] nums = {10, 20, 30, 100, 200, 500};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initData() {
        initTitle("充值余额");
        rlv = findViewById(R.id.rlv_recharge);
        findViewById(R.id.ll_al_pay).setOnClickListener(this);
        findViewById(R.id.ll_wx_pay).setOnClickListener(this);
        ivAlPay = findViewById(R.id.iv_al_pay);
        ivWxPay = findViewById(R.id.iv_wx_pay);
        for (int num : nums) {
            RechargeBean bean = new RechargeBean();
            bean.setNum(num);
            list.add(bean);
        }
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 3);
        rlv.setLayoutManager(manager);
        adapter = new RechargeAdapter(list);
        rlv.setAdapter(adapter);
        rlv.setHasFixedSize(true);
        adapter.setOnItemClickListener((view, position) -> {
            rechargeNum = list.get(position).getNum();
            list.get(prePosition).setCheck(false);
            list.get(position).setCheck(true);
            adapter.notifyItemChanged(position);
            adapter.notifyItemChanged(prePosition);
            prePosition = position;
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_al_pay:
                ivAlPay.setVisibility(View.VISIBLE);
                ivWxPay.setVisibility(View.GONE);
                break;
            case R.id.ll_wx_pay:
                ivAlPay.setVisibility(View.GONE);
                ivWxPay.setVisibility(View.VISIBLE);
                break;
        }
    }
}
