package com.anyidc.cloudpark.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.BankInfoUtil;
import com.anyidc.cloudpark.utils.ToastUtil;

/**
 * Created by Administrator on 2018/3/16.
 */

public class BindBankCardActivity extends BaseActivity implements TextWatcher {
    private EditText etRealName, etIdNum, etBankNum, etPhoneNum;
    private TextView tvBank;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_bank_card;
    }

    @Override
    protected void initData() {
        initTitle("添加银行卡");
        etRealName = findViewById(R.id.et_real_name);
        etIdNum = findViewById(R.id.et_id_num);
        etBankNum = findViewById(R.id.et_bank_card_num);
        etBankNum.addTextChangedListener(this);
        etPhoneNum = findViewById(R.id.et_phone_num);
        tvBank = findViewById(R.id.tv_bank);
        findViewById(R.id.btn_next_step).setOnClickListener(clickListener);
    }

    @Override
    public void onCheckDoubleClick(View v) {
        addBankCard();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String bankNum = etBankNum.getText().toString();
        if (bankNum.length() > 15 && bankNum.length() < 20) {
            String nameOfBank = BankInfoUtil.getNameOfBank(this, Long.parseLong(bankNum.substring(0, 6)));
            if (nameOfBank.startsWith("磁条卡卡号")) {
                ToastUtil.showToast("无法识别该卡号，请检查是否输入错误", Toast.LENGTH_SHORT);
                return;
            }
            tvBank.setText(nameOfBank);
        } else {
            tvBank.setText("");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void addBankCard() {
        String realName = etRealName.getText().toString();
        String idNum = etIdNum.getText().toString();
        String cardNum = etBankNum.getText().toString();
        String bank = tvBank.getText().toString();
        String phoneNum = etPhoneNum.getText().toString();
        if (TextUtils.isEmpty(realName)) {
            ToastUtil.showToast("请填写您的真实姓名", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(idNum) || idNum.length() != 15 && idNum.length() != 18) {
            ToastUtil.showToast("请填写正确的身份证号码", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(cardNum) || cardNum.length() != 16 && cardNum.length() != 19 && cardNum.length() != 18) {
            ToastUtil.showToast("请填写正确的银行卡号", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(bank)) {
            ToastUtil.showToast("请填写正确的银行卡号", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(phoneNum) || phoneNum.length() != 11) {
            ToastUtil.showToast("请填写正确的手机号码", Toast.LENGTH_SHORT);
            return;
        }
        getTime(Api.getDefaultService().addBankCard(bank, cardNum, phoneNum, realName, idNum)
                , new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }
}
