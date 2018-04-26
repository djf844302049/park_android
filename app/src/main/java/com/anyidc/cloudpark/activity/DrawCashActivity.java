package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.BankChoiceAdapter;
import com.anyidc.cloudpark.moduel.BankCardBean;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.DrawCashBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */

public class DrawCashActivity extends BaseActivity {
    private BottomSheetDialog dialog;
    private RecyclerView rlvBank;
    private BankChoiceAdapter adapter;
    private WeakReference<Context> reference;
    private TextView tvBankCard, tvRemainBalance;
    private EditText etDrawNum;
    private List<BankCardBean> list = new ArrayList<>();
    private String drawNum, bank_id;

    public static void actionStart(Context context, String balance) {
        Intent intent = new Intent(context, DrawCashActivity.class);
        intent.putExtra("balance", balance);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_draw_cash;
    }

    @Override
    protected void initData() {
        initTitle("余额提现");
        String balance = getIntent().getStringExtra("balance");
        tvBankCard = findViewById(R.id.tv_bank);
        tvRemainBalance = findViewById(R.id.tv_remain_balance);
        tvRemainBalance.setText("当前账户余额" + balance + "元");
        etDrawNum = findViewById(R.id.et_draw_num);
        reference = new WeakReference<>(this);
        dialog = new BottomSheetDialog(reference.get());
        dialog.setContentView(R.layout.dialog_bankcard_picker);
        rlvBank = dialog.findViewById(R.id.rlv_bank_card);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rlvBank.setLayoutManager(manager);
        adapter = new BankChoiceAdapter(list);
        rlvBank.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> {
            dialog.dismiss();
            if (position == list.size() - 1) {
                startActivity(new Intent(DrawCashActivity.this, BindBankCardActivity.class));
                return;
            }
            tvBankCard.setText(list.get(position).getCardInfo());
            bank_id = list.get(position).getBank_id();
        });
        findViewById(R.id.rl_bank_choice).setOnClickListener(clickListener);
        findViewById(R.id.btn_draw_cash).setOnClickListener(clickListener);
    }


    @Override
    public void onCheckDoubleClick(View v) {
        switch (v.getId()) {
            case R.id.rl_bank_choice:
                getBankCardList();
                break;
            case R.id.btn_draw_cash:
                drawCash();
                break;
        }
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

    private void drawCash() {
        drawNum = etDrawNum.getText().toString().trim();
        if (TextUtils.isEmpty(drawNum) || TextUtils.isEmpty(bank_id)) {
            return;
        }
        getTime(Api.getDefaultService().drawCash(drawNum, bank_id),
                new RxObserver<BaseEntity<DrawCashBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<DrawCashBean> baseEntity) {
                        DrawCashBean bean = baseEntity.getData();
                        DrawCashResultActivity.actionStart(DrawCashActivity.this, bean);
                        finish();
                    }
                });
    }
}
