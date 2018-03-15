package com.anyidc.cloudpark.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anyidc.cloudpark.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */

public class PayParkActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private EditText etNum;
    private List<TextView> tvList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_park;
    }

    @Override
    protected void initData() {
        initTitle("停车付费");
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
        findViewById(R.id.btn_go_pay).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

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
            tvList.get(i).setText(String.valueOf(chars[i]));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        etNum.removeTextChangedListener(this);
    }
}
