package com.anyidc.cloudpark.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.utils.CacheData;
import com.anyidc.cloudpark.utils.LicenseKeyboardUtil;
import com.anyidc.cloudpark.utils.LoginUtil;
import com.anyidc.cloudpark.utils.ToastUtil;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import static com.anyidc.cloudpark.activity.MineActivity.LOGIN;

/**
 * Created by Administrator on 2018/3/13.
 */

public class PayParkActivity extends BaseActivity implements TextWatcher {
    private EditText etNum;
    private List<TextView> tvList;
    private String unitId;
    private LicenseKeyboardUtil keyboardUtil;
    private ImageView ivRight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_park;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        initTitle("停车付费");

        ivRight = findViewById(R.id.iv_right);//右上二维码扫描
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.qr_code);
        ivRight.setOnClickListener(clickListener);

        findViewById(R.id.btn_qrcode).setOnClickListener(clickListener);//二维码扫描按钮

        findViewById(R.id.text_delete_all).setOnClickListener(clickListener);//清空按钮

        TextView tv1 = findViewById(R.id.tv_num_1);
        TextView tv2 = findViewById(R.id.tv_num_2);
        TextView tv3 = findViewById(R.id.tv_num_3);
        TextView tv4 = findViewById(R.id.tv_num_4);
        TextView tv5 = findViewById(R.id.tv_num_5);
        TextView tv6 = findViewById(R.id.tv_num_6);
        tvList = new ArrayList<>();
        tvList.add(tv1);
        tvList.add(tv2);
        tvList.add(tv3);
        tvList.add(tv4);
        tvList.add(tv5);
        tvList.add(tv6);
        etNum = findViewById(R.id.et_num);
        keyboardUtil = new LicenseKeyboardUtil(PayParkActivity.this, etNum, 0);
        etNum.addTextChangedListener(this);
        etNum.setOnTouchListener((v, event) -> {
            keyboardUtil.showKeyboard();
            return false;
        });

        findViewById(R.id.btn_go_pay).setOnClickListener(clickListener);
        findViewById(R.id.rl_root).setOnClickListener(v -> {
            if (keyboardUtil.isShow()) {
                keyboardUtil.hideKeyboard();
            }
        });
    }

    @Override
    public void onCheckDoubleClick(View v) {
        switch (v.getId()){
            case R.id.iv_right://右上二维码扫码
                if (LoginUtil.isLogin()) {
                    startActivityForResult(new Intent(PayParkActivity.this, CaptureActivity.class), 0);
                } else
                    startActivityForResult(new Intent(this, LoginActivity.class), LOGIN);
                break;

            case R.id.btn_qrcode://二维码扫描按钮
                if (LoginUtil.isLogin()) {
                    startActivityForResult(new Intent(PayParkActivity.this, CaptureActivity.class), 0);
                } else
                    startActivityForResult(new Intent(this, LoginActivity.class), LOGIN);
                break;

            case R.id.btn_go_pay://去支付按钮
                if (TextUtils.isEmpty(unitId) || unitId.length() != 6) {
                    return;
                }
                ParkChargeActivity.actionStart(this, unitId);
                break;

            case R.id.text_delete_all:
                etNum.setText("");
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String result = data.getExtras().getString("result");
//            etNum.setText(result);//将获取到的返回值设置到输入框上
            char[] chars = result.toCharArray();

            Editable editable = etNum.getText();
            int start = etNum.getSelectionStart();//获取光标位置

            for(int i = 5 ; i >= 0 ; i-- ){
                editable.insert(start, Character.toString(chars[i]));
            }

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
        unitId = s.toString();
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
    protected void onDestroy() {
        super.onDestroy();
        etNum.removeTextChangedListener(this);
    }
}
