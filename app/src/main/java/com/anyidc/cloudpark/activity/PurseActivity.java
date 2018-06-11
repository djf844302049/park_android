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

public class PurseActivity extends BaseActivity {
    private TextView tvBalance;
    private TextView tvDepositState;
    private Button btnDeposit;
    private Button btnDrawCash;
    private TextView tvRight;
    private String balance;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_purse;
    }

    @Override
    protected void initData() {
        tvBalance = findViewById(R.id.tv_balance_num);
        tvDepositState = findViewById(R.id.tv_deposit_state);
        btnDeposit = findViewById(R.id.btn_recharge_deposit);
        findViewById(R.id.btn_recharge_balance).setOnClickListener(clickListener);
        btnDeposit.setOnClickListener(clickListener);
        tvRight = findViewById(R.id.tv_right);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("交易明细");
        tvRight.setOnClickListener(clickListener);
        findViewById(R.id.tv_pay_setting).setOnClickListener(clickListener);
        btnDrawCash = findViewById(R.id.btn_draw_cash);
        btnDrawCash.setOnClickListener(clickListener);
        initTitle("我的钱包");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWalletInfo();
    }

    @Override
    public void onCheckDoubleClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recharge_deposit:
                if ("缴纳押金".equals(btnDeposit.getText().toString().trim()))
                    startActivity(new Intent(this, DepositActivity.class));
                else {

                }
                break;
            case R.id.tv_pay_setting:
                startActivity(new Intent(this, PaySettingActivity.class));
                break;
            case R.id.btn_draw_cash:
                DrawCashActivity.actionStart(this, balance);
                break;
            case R.id.tv_right:
                startActivity(new Intent(this, TransactionDetailActivity.class));
                break;
            case R.id.btn_recharge_balance:
//                if ("缴纳押金".equals(btnDeposit.getText().toString().trim())) {
//                    new AlertDialog.Builder(this)
//                            .setTitle("充值提示")
//                            .setMessage("充值余额前需缴纳**押金")
//                            .setPositiveButton("缴纳押金", (dialog, which) ->
//                                    startActivity(new Intent(this, DepositActivity.class)))
//                            .setNegativeButton("取消", null)
//                            .show();
//                    return;
//                }
                startActivity(new Intent(this, RechargeActivity.class));
                break;
        }
    }

    private void getWalletInfo() {
        getTime(Api.getDefaultService().getWalletInfo(),
                new RxObserver<BaseEntity<WalletInfoBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<WalletInfoBean> baseEntity) {
                        WalletInfoBean data = baseEntity.getData();
                        balance = data.getUser_money();
                        tvBalance.setText("￥" + balance);
                        if (data.getDeposit_flag() == 1) {
                            tvDepositState.setText("已缴纳");
                            btnDeposit.setText("退回押金");
//                            btnDrawCash.setEnabled(true);
                        } else {
                            tvDepositState.setText("未缴纳");
                            btnDeposit.setText("缴纳押金");
//                            btnDrawCash.setEnabled(false);
                        }
                    }
                });
    }
}
