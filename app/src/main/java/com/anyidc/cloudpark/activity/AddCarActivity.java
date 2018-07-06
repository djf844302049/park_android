package com.anyidc.cloudpark.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

/**
 * Created by Administrator on 2018/2/23.
 */

public class AddCarActivity extends BaseActivity implements TextWatcher {
    private EditText etNum;
    private CheckBox cbNewEnergy;
    private Button btnAdd;
    private int isNewEnergy;
    private List<TextView> tvList;
    private TextView tvSkip;
    private int from;
    private String carNum;
    private LicenseKeyboardUtil keyboardUtil;

    public static void actionStart(Context context, int from) {
        Intent intent = new Intent(context, AddCarActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_car;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        clickListener = new CheckDoubleClickListener(this);
        tvSkip = findViewById(R.id.tv_skip);
        tvSkip.setOnClickListener(clickListener);
        tvList = new ArrayList<>();
        etNum = findViewById(R.id.et_num);
        keyboardUtil = new LicenseKeyboardUtil(this, etNum, 1);
        etNum.addTextChangedListener(this);
        etNum.setOnTouchListener((v, event) -> {
            keyboardUtil.showKeyboard();
            return false;
        });
        btnAdd = findViewById(R.id.btn_confirm_add);
        btnAdd.setOnClickListener(clickListener);
        cbNewEnergy = findViewById(R.id.cb_new_energy_car);
        cbNewEnergy.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                isNewEnergy = 1;
                etNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
            } else {
                isNewEnergy = 0;
                etNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
                if (carNum != null && carNum.length() > 7) {
                    String substring = carNum.substring(0, 7);
                    etNum.setText(substring);
                    etNum.setSelection(substring.length());
                }
            }
        });
        initTitle("添加车辆");
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
                break;
        }
    }
}
