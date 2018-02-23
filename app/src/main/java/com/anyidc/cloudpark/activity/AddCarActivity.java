package com.anyidc.cloudpark.activity;

import android.text.Editable;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/23.
 */

public class AddCarActivity extends BaseActivity implements TextWatcher, View.OnClickListener {
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private EditText etNum;
    private CheckBox cbNewEnergy;
    private Button btnAdd;
    private int isNewEnergy;
    private List<TextView> tvList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_car;
    }

    @Override
    protected void initData() {
        tv1 = findViewById(R.id.tv_num_1);
        tv2 = findViewById(R.id.tv_num_2);
        tv3 = findViewById(R.id.tv_num_3);
        tv4 = findViewById(R.id.tv_num_4);
        tv5 = findViewById(R.id.tv_num_5);
        tv6 = findViewById(R.id.tv_num_6);
        tv7 = findViewById(R.id.tv_num_7);
        tvList = new ArrayList<>();
        tvList.add(tv1);
        tvList.add(tv2);
        tvList.add(tv3);
        tvList.add(tv4);
        tvList.add(tv5);
        tvList.add(tv6);
        tvList.add(tv7);
        etNum = findViewById(R.id.et_num);
        etNum.addTextChangedListener(this);
        btnAdd = findViewById(R.id.btn_confirm_add);
        btnAdd.setOnClickListener(this);
        cbNewEnergy = findViewById(R.id.cb_new_energy_car);
        cbNewEnergy.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                isNewEnergy = 1;
            } else {
                isNewEnergy = 0;
            }
        });
        initTitle("添加车辆");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
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
        String carNum = etNum.getText().toString();
        if (TextUtils.isEmpty(carNum) || carNum.length() != 7) {
            Toast.makeText(this, "车牌号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        getTime(Api.getDefaultService().addCar(carNum, isNewEnergy)
                , new RxObserver<BaseEntity<AddCarBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<AddCarBean> addCarBean) {
                        CarConfirmActivity.actionStart(AddCarActivity.this, addCarBean.getData().getId());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        etNum.removeTextChangedListener(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        commitAddCar();
    }
}
