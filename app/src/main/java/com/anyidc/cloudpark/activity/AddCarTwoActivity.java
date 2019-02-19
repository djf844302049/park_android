package com.anyidc.cloudpark.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import com.anyidc.cloudpark.utils.LicenseKeyboardUtil;
import com.anyidc.cloudpark.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/*
* 车牌号为框框的界面
* */
public class AddCarTwoActivity extends BaseActivity implements TextWatcher {

    private EditText etNum;//车牌号输入框
    private int isNewEnergy;//是否新能源汽车
    private int from;//来源码
    private String carNum;//车牌号
    private LicenseKeyboardUtil keyboardUtil;//键盘工具类
    private TextView tvnum1,tvnum2,tvnum3,tvnum4,tvnum5,tvnum6,tvnum7,tvnum8;
    private List<TextView> tvnumList;

    public static void actionStart(Context context, int from) {
        Intent intent = new Intent(context, AddCarTwoActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_car_two;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        initTitle("添加车辆");
        tvnum1 = findViewById(R.id.tv_num_1);
        tvnum2 = findViewById(R.id.tv_num_2);
        tvnum3 = findViewById(R.id.tv_num_3);
        tvnum4 = findViewById(R.id.tv_num_4);
        tvnum5 = findViewById(R.id.tv_num_5);
        tvnum6 = findViewById(R.id.tv_num_6);
        tvnum7 = findViewById(R.id.tv_num_7);
        tvnum8 = findViewById(R.id.tv_num_8);
        tvnumList = new ArrayList<>();
        tvnumList.add(tvnum1);
        tvnumList.add(tvnum2);
        tvnumList.add(tvnum3);
        tvnumList.add(tvnum4);
        tvnumList.add(tvnum5);
        tvnumList.add(tvnum6);
        tvnumList.add(tvnum7);
        tvnumList.add(tvnum8);
        clickListener = new CheckDoubleClickListener(this);
        TextView tvSkip = findViewById(R.id.tv_skip);
        tvSkip.setOnClickListener(clickListener);
        etNum = findViewById(R.id.et_num);
        keyboardUtil = new LicenseKeyboardUtil(this, etNum, 1);
        etNum.addTextChangedListener(this);
        etNum.setOnTouchListener((v, event) -> {
            keyboardUtil.showKeyboard();
            return false;
        });
        Button btnAdd = findViewById(R.id.btn_confirm_add);//确认添加
        btnAdd.setOnClickListener(clickListener);
        CheckBox cbNewEnergy = findViewById(R.id.cb_new_energy_car);//复选框，勾选代表新能源汽车
        cbNewEnergy.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {//是新能源汽车
                isNewEnergy = 1;
                tvnum8.setVisibility(View.VISIBLE);
                etNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});//设置输入框最大数为8个
            } else {
                isNewEnergy = 0;
                tvnum8.setVisibility(View.GONE);
                etNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});//最大7个
                if (carNum != null && carNum.length() > 7) {
                    //如果点击新能源汽车复选框输入8位字符，再点击取消复选框时，获取前七位设置到输入框中
                    String substring = carNum.substring(0, 7);
                    etNum.setText(substring);
                    etNum.setSelection(substring.length());
                }
            }
        });
        findViewById(R.id.rl_root).setOnClickListener(v -> {
            if (keyboardUtil.isShow()) {
                keyboardUtil.hideKeyboard();
            }
        });
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
                        CarConfirmActivity.actionStart(AddCarTwoActivity.this, addCarBean.getData().getId(), from);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyboardUtil.isShow()) {
                keyboardUtil.hideKeyboard();
            } else {
                finish();
            }
        }
        return false;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_add:
                commitAddCar();
                break;
            case R.id.tv_skip:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void setNum(String num) {
        char[] chars = num.toCharArray();//将形参上的字符分解成单字符并存入char类型的数组中
        int length = chars.length;//获取数组长度
        for (TextView textView : tvnumList) {
            textView.setText("");
        }//清空框内字符
        for (int i = 0; i < length; i++) {
            tvnumList.get(i).setText(String.valueOf(chars[i]));
        }//以数组长度为length值遍历TextView的集合，将分解好的chars数组依次setText到遍历的TextView的控件上
    }
}
