package com.anyidc.cloudpark.activity;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.BankChoiceAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */

public class DrawCashActivity extends BaseActivity implements View.OnClickListener {
    private BottomSheetDialog dialog;
    private RecyclerView rlvBank;
    private BankChoiceAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_draw_cash;
    }

    @Override
    protected void initData() {
        initTitle("余额提现");
        list.add("使用新卡提现");
        dialog = new BottomSheetDialog(this);
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
            }
        });
        findViewById(R.id.rl_bank_choice).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        dialog.show();
    }
}
