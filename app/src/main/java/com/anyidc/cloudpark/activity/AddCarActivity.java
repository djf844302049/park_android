package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.AddCarBean;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.CheckDoubleClickListener;
import com.anyidc.cloudpark.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/23.
 */

public class AddCarActivity extends BaseActivity implements TextWatcher {
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private EditText etNum;
    private CheckBox cbNewEnergy;
    private Button btnAdd;
    private int isNewEnergy;
    private List<TextView> tvList;
    private TextView tvSkip;
    private int from;
    private String carNum;

    public static void actionStart(Context context, int from) {
        Intent intent = new Intent(context, AddCarActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_car;
    }

    @Override
    protected void initData() {
        clickListener = new CheckDoubleClickListener(this);
        tv1 = findViewById(R.id.tv_num_1);
        tv2 = findViewById(R.id.tv_num_2);
        tv3 = findViewById(R.id.tv_num_3);
        tv4 = findViewById(R.id.tv_num_4);
        tv5 = findViewById(R.id.tv_num_5);
        tv6 = findViewById(R.id.tv_num_6);
        tv7 = findViewById(R.id.tv_num_7);
        tv8 = findViewById(R.id.tv_num_8);
        tvSkip = findViewById(R.id.tv_skip);
        tvSkip.setOnClickListener(clickListener);
        tvList = new ArrayList<>();
        tvList.add(tv1);
        tvList.add(tv2);
        tvList.add(tv3);
        tvList.add(tv4);
        tvList.add(tv5);
        tvList.add(tv6);
        tvList.add(tv7);
        tvList.add(tv8);
        etNum = findViewById(R.id.et_num);
        etNum.addTextChangedListener(this);
        btnAdd = findViewById(R.id.btn_confirm_add);
        btnAdd.setOnClickListener(clickListener);
        cbNewEnergy = findViewById(R.id.cb_new_energy_car);
        cbNewEnergy.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                isNewEnergy = 1;
                etNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                tv8.setVisibility(View.VISIBLE);
            } else {
                isNewEnergy = 0;
                etNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
                if (carNum != null && carNum.length() > 7) {
                    String substring = carNum.substring(0, 7);
                    etNum.setText(substring);
                    etNum.setSelection(substring.length());
                }
                tv8.setVisibility(View.GONE);
            }
        });
        initTitle("添加车辆");
        from = getIntent().getIntExtra("from", 0);
        switch (from) {
            case 1:
                break;
            default:
                tvSkip.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        carNum = editable.toString();
        setNum(editable.toString());
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

    private void commitAddCar() {
        if (TextUtils.isEmpty(carNum)) {
            return;
        }
        if ((isNewEnergy == 0 && carNum.length() != 7) || (isNewEnergy == 1 && carNum.length() != 8)) {
            ToastUtil.showToast("车牌号格式不正确", Toast.LENGTH_SHORT);
            return;
        }
        getTime(Api.getDefaultService().addCar(carNum, isNewEnergy)
                , new RxObserver<BaseEntity<AddCarBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<AddCarBean> addCarBean) {
                        CarConfirmActivity.actionStart(AddCarActivity.this, addCarBean.getData().getId(), from);
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        etNum.removeTextChangedListener(this);
        super.onDestroy();
    }

    @Override
    public void onCheckDoubleClick(View view) {
        super.onCheckDoubleClick(view);
        switch (view.getId()) {
            case R.id.btn_confirm_add:
                commitAddCar();
                break;
            case R.id.tv_skip:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
