package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.DrawCashBean;

/**
 * Created by Administrator on 2018/3/19.
 */

public class DrawCashResultActivity extends BaseActivity {
    private TextView tvSn, tvDate, tvDrawNum, tvDrawSuccess, tvDrawSuccessTip;

    public static void actionStart(Context context, DrawCashBean bean, int from) {
        Intent intent = new Intent(context, DrawCashResultActivity.class);
        intent.putExtra("bean", bean);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_draw_cash_result;
    }

    @Override
    protected void initData() {
        DrawCashBean bean = (DrawCashBean) getIntent().getSerializableExtra("bean");
        int from = getIntent().getIntExtra("from", 0);
        tvSn = findViewById(R.id.tv_draw_sn);
        tvDate = findViewById(R.id.tv_draw_date);
        tvDrawNum = findViewById(R.id.tv_draw_num);
        tvDrawSuccess = findViewById(R.id.tv_text_draw_success);
        tvDrawSuccessTip = findViewById(R.id.tv_text_draw_success_tip);
        String head;
        switch (from) {
            case 1:
                initTitle("押金退回");
                head = "押金退回单号：";
                tvDrawSuccess.setText("押金退回成功");
                tvDrawSuccessTip.setText("已经成功退回押金，预计0-3个工作日到账");
                break;
            default:
                initTitle("提现");
                head = "提现单号：";
                break;
        }
        if (bean != null) {
            tvDrawNum.setText("￥" + bean.getMoney());
            tvSn.setText(head + bean.getOrder_id());
            tvDate.setText(bean.getDate());
        }
    }
}
