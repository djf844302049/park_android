package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.WalletInfoBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

/**
 * Created by Administrator on 2018/3/12.
 */

public class PurseActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvBalance;
    private TextView tvDepositState;
    private Button btnDeposit;
    private Button btnDrawCash;
    private TextView tvRight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_purse;
    }

    @Override
    protected void initData() {
        tvBalance = findViewById(R.id.tv_balance_num);
        tvDepositState = findViewById(R.id.tv_deposit_state);
        btnDeposit = findViewById(R.id.btn_recharge_deposit);
        btnDeposit.setOnClickListener(this);
        tvRight = findViewById(R.id.tv_right);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("交易明细");
        tvRight.setOnClickListener(this);
        findViewById(R.id.tv_pay_setting).setOnClickListener(this);
        btnDrawCash = findViewById(R.id.btn_draw_cash);
        btnDrawCash.setOnClickListener(this);
        initTitle("我的钱包");
        getWalletInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recharge_deposit:
                break;
            case R.id.tv_pay_setting:
                startActivity(new Intent(this, PaySettingActivity.class));
                break;
            case R.id.btn_draw_cash:
                break;
            case R.id.tv_right:
                startActivity(new Intent(this, TransactionDetailActivity.class));
                break;
        }
    }

    private void getWalletInfo() {
        getTime(Api.getDefaultService().getWalletInfo(),
                new RxObserver<BaseEntity<WalletInfoBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<WalletInfoBean> baseEntity) {
                        WalletInfoBean data = baseEntity.getData();
                        tvBalance.setText("￥" + data.getUser_money());
                        if ("0.00".equals(data.getUser_money())) {
                            tvDepositState.setText("未缴纳");
                            btnDeposit.setText("缴纳押金");
                            btnDrawCash.setEnabled(false);
                        } else {
                            tvDepositState.setText("已缴纳");
                            btnDeposit.setText("退回押金");
                            btnDrawCash.setEnabled(true);
                        }
                    }
                });
    }
}
