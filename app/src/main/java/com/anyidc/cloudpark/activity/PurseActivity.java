package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.BankChoiceAdapter;
import com.anyidc.cloudpark.dialog.ConfirmCancelDialog;
import com.anyidc.cloudpark.moduel.BankCardBean;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.DrawCashBean;
import com.anyidc.cloudpark.moduel.WalletInfoBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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
    private List<BankCardBean> list = new ArrayList<>();
    private BottomSheetDialog dialog;
    private RecyclerView rlvBank;
    private BankChoiceAdapter adapter;
    private int bank_id;
    private WeakReference<Context> reference;
    private ConfirmCancelDialog confirmDialog;
    private final int ADDBANKCARD = 999;
    private float depositNum;
    private int depositInYear;

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
        reference = new WeakReference<>(this);
        confirmDialog = new ConfirmCancelDialog(reference.get(), "", "您确认退回押金吗？", "确认", "取消");
        confirmDialog.setClickListener(new ConfirmCancelDialog.ClickListener() {
            @Override
            public void confirm() {
                drawDeposit();
                confirmDialog.dismiss();
            }

            @Override
            public void cancel() {
                confirmDialog.dismiss();
            }
        });
        dialog = new BottomSheetDialog(reference.get());
        dialog.setContentView(R.layout.dialog_bankcard_picker);
        rlvBank = (RecyclerView) dialog.findViewById(R.id.rlv_bank_card);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rlvBank.setLayoutManager(manager);
        adapter = new BankChoiceAdapter(list);
        rlvBank.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> {
            dialog.dismiss();
            if (position == list.size() - 1) {
                startActivityForResult(new Intent(this, BindBankCardActivity.class), ADDBANKCARD);
                return;
            }
            bank_id = list.get(position).getBank_id();
            drawDeposit();
        });
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
                    DepositActivity.actionStart(PurseActivity.this, depositNum);
                else if (depositInYear == 0) {
                    if (list.size() == 0) {
                        getBankCardList();
                    } else {
                        dialog.show();
                    }
                } else if (depositInYear == 1) {
                    confirmDialog.show();
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
                if ("缴纳押金".equals(btnDeposit.getText().toString().trim())) {
                    new AlertDialog.Builder(this)
                            .setTitle("充值提示")
                            .setMessage("充值余额前需缴纳" + String.valueOf(depositNum) + "押金")
                            .setPositiveButton("缴纳押金", (dialog, which) ->
                                    DepositActivity.actionStart(PurseActivity.this, depositNum))
                            .setNegativeButton("取消", null)
                            .show();
                    return;
                }
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
                        depositNum = data.getNeed_deposit();
                        tvBalance.setText("￥" + balance);
                        depositInYear = data.getDeposit_InYear();
                        if (data.getDeposit_flag() == 1) {
                            tvDepositState.setText("已缴纳");
                            btnDeposit.setText("退回押金");
                            btnDrawCash.setEnabled(true);
                        } else {
                            tvDepositState.setText("未缴纳");
                            btnDeposit.setText("缴纳押金");
                            btnDrawCash.setEnabled(false);
                        }
                    }
                });
    }

    private void getBankCardList() {
        getTime(Api.getDefaultService().getBankCard(),
                new RxObserver<BaseEntity<List<BankCardBean>>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<List<BankCardBean>> baseEntity) {
                        list.clear();
                        list.addAll(baseEntity.getData());
                        list.add(new BankCardBean());
                        adapter.notifyDataSetChanged();
                        dialog.show();
                    }
                });
    }

    private void drawDeposit() {
        getTime(Api.getDefaultService().drawDeposit(bank_id, 0, "")
                , new RxObserver<BaseEntity<DrawCashBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<DrawCashBean> baseEntity) {
                        ToastUtil.showToast(baseEntity.getMessage(), Toast.LENGTH_SHORT);
                        DrawCashResultActivity.actionStart(PurseActivity.this, baseEntity.getData(), 1);
                        btnDeposit.setText("缴纳押金");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADDBANKCARD) {
            if (resultCode == RESULT_OK) {
                getBankCardList();
            }
        }
    }
}
