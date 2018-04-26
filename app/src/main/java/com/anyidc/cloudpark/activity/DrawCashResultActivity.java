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
    private TextView tvSn, tvDate, tvDrawNum;

    public static void actionStart(Context context, DrawCashBean bean) {
        Intent intent = new Intent(context, DrawCashResultActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_draw_cash_result;
    }

    @Override
    protected void initData() {
        initTitle("提现");
        DrawCashBean bean = (DrawCashBean) getIntent().getSerializableExtra("bean");
        tvSn = findViewById(R.id.tv_draw_sn);
        tvDate = findViewById(R.id.tv_draw_date);
        tvDrawNum = findViewById(R.id.tv_draw_num);
        if (bean != null) {
            tvDrawNum.setText("￥" + bean.getMoney());
            tvSn.setText("提现单号：" + bean.getOrder_id());
            tvDate.setText(bean.getDate());
        }
    }
}
