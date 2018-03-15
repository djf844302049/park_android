package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anyidc.cloudpark.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */

public class PayKeySetActivity extends BaseActivity implements TextWatcher {

    private TextView tvDesc;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tvErrDesc;
    private EditText etNum;
    private List<TextView> tvList;
    private String firstInput;
    private String preKey;
    private int inputTime;
    private int from;

    public static void actionStart(Context context, int from) {
        Intent intent = new Intent(context, PayKeySetActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_about_pay_key;
    }

    @Override
    protected void initData() {
        tvDesc = findViewById(R.id.tv_desc);
        tvErrDesc = findViewById(R.id.tv_err_desc);
        tv1 = findViewById(R.id.tv_num_1);
        tv2 = findViewById(R.id.tv_num_2);
        tv3 = findViewById(R.id.tv_num_3);
        tv4 = findViewById(R.id.tv_num_4);
        tv5 = findViewById(R.id.tv_num_5);
        tv6 = findViewById(R.id.tv_num_6);
        tvList = new ArrayList<>();
        tvList.add(tv1);
        tvList.add(tv2);
        tvList.add(tv3);
        tvList.add(tv4);
        tvList.add(tv5);
        tvList.add(tv6);
        etNum = findViewById(R.id.et_num);
        etNum.addTextChangedListener(this);
        from = getIntent().getIntExtra("from", 0);
        switch (from) {
            case 1:
                initTitle("设置支付密码");
                tvDesc.setText("请设置支付密码，用于支付验证");
                break;
            case 2:
                initTitle("身份认证");
                tvDesc.setText("请输入支付密码");
                break;
            case 3:
                initTitle("设置新密码");
                tvDesc.setText("请设置新支付密码，用于支付验证");
                break;
            case 4:
                initTitle("身份认证");
                tvDesc.setText("请输入支付密码");
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        setNum(s.toString());
    }

    private void setNum(String num) {
        char[] chars = num.toCharArray();
        int length = chars.length;
        for (TextView textView : tvList) {
            textView.setText("");
        }
        for (int i = 0; i < length; i++) {
            tvList.get(i).setText("●");
        }
        if (length == 6) {
            inputTime++;
            switch (inputTime) {
                case 1:
                    if (tvErrDesc.getVisibility() == View.VISIBLE) {
                        tvErrDesc.setVisibility(View.GONE);
                    }
                    switch (from) {
                        case 1:
                        case 3:
                            firstInput = num;
                            tvDesc.setText("请再次输入，以确认密码");
                            etNum.setText("");
                            break;
                        case 2:
                            preKey = num;
                            tvDesc.setText("请设置新支付密码，用于支付验证");
                            initTitle("新支付密码");
                            etNum.setText("");
                            break;
                        case 4:
                            startActivity(new Intent(this, PayWithoutKeyActivity.class));
                            break;
                    }
                    break;
                case 2:
                    switch (from) {
                        case 1:
                        case 3:
                            if (num.equals(firstInput)) {

                            } else {
                                tvErrDesc.setVisibility(View.VISIBLE);
                                tvDesc.setText("请设置支付密码，用于支付验证");
                                inputTime = 0;
                                etNum.setText("");
                            }
                            break;
                        case 2:
                            if (tvErrDesc.getVisibility() == View.VISIBLE) {
                                tvErrDesc.setVisibility(View.GONE);
                            }
                            firstInput = num;
                            tvDesc.setText("请再次输入，以确认密码");
                            etNum.setText("");
                            break;
                    }
                    break;
                case 3:
                    if (!num.equals(firstInput)) {
                        tvErrDesc.setText("两次输入不一致，请重新设置");
                        tvErrDesc.setVisibility(View.VISIBLE);
                        tvDesc.setText("请设置新支付密码，用于支付验证");
                        inputTime = 1;
                        etNum.setText("");
                    } else if (num.equals(preKey)) {
                        tvErrDesc.setText("新密码与当前密码相同，请重新设置");
                        tvErrDesc.setVisibility(View.VISIBLE);
                        tvDesc.setText("请设置新支付密码，用于支付验证");
                        inputTime = 1;
                        etNum.setText("");
                    } else {

                    }
                    break;
            }
        }
    }
}
