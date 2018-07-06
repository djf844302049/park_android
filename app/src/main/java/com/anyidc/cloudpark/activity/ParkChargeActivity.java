package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.dialog.BaseDialog;
import com.anyidc.cloudpark.moduel.AlPayBean;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.ParkInfoBean;
import com.anyidc.cloudpark.moduel.WxPayBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.AesUtil;
import com.anyidc.cloudpark.utils.AlPayResultHandler;
import com.anyidc.cloudpark.utils.CacheData;
import com.anyidc.cloudpark.utils.LoginUtil;
import com.anyidc.cloudpark.utils.WxPayHelper;
import com.anyidc.cloudpark.wxapi.WXPayEntryActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/16.
 */

public class ParkChargeActivity extends BaseActivity implements TextWatcher {
    private TextView tvChargeNum, tvParkDuration, tvSharePark, tvDiscount;
    private ImageView ivHelp, ivBalancePay, ivAlPay, ivWxPay;
    private String unitId;
    private AlPayResultHandler mHandler;
    private LinearLayout llBalancePay;
    private String rechargeNum;
    private int payType;
    private BaseDialog payDialog;
    private String payKey;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tvChargeRules;
    private EditText etNum;
    private List<TextView> tvList;
    private BaseDialog rulesDialog;
    private Button btnPay;

    public static void actionStart(Context context, String unitId) {
        Intent intent = new Intent(context, ParkChargeActivity.class);
        intent.putExtra("unitId", unitId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_park;
    }

    @Override
    protected void initData() {
        initTitle("停车付费");
        unitId = getIntent().getStringExtra("unitId");
        getParkInfo();
        tvChargeNum = findViewById(R.id.tv_park_charge_num);
        tvParkDuration = findViewById(R.id.tv_park_duration);
        tvSharePark = findViewById(R.id.tv_share_park);
        tvDiscount = findViewById(R.id.tv_discount);
        ivHelp = findViewById(R.id.iv_help);
        ivHelp.setOnClickListener(clickListener);
        ivAlPay = findViewById(R.id.iv_al_pay);
        ivWxPay = findViewById(R.id.iv_wx_pay);
        ivBalancePay = findViewById(R.id.iv_balance_pay);
        btnPay = findViewById(R.id.btn_pay);
        btnPay.setOnClickListener(clickListener);
        llBalancePay = findViewById(R.id.ll_balance_pay);
        llBalancePay.setOnClickListener(v -> {
            ivBalancePay.setVisibility(View.VISIBLE);
            ivAlPay.setVisibility(View.GONE);
            ivWxPay.setVisibility(View.GONE);
            payType = 4;
        });
        findViewById(R.id.ll_al_pay).setOnClickListener(v -> {
            payType = 1;
            ivBalancePay.setVisibility(View.GONE);
            ivAlPay.setVisibility(View.VISIBLE);
            ivWxPay.setVisibility(View.GONE);
        });
        findViewById(R.id.ll_wx_pay).setOnClickListener(v -> {
            payType = 2;
            ivBalancePay.setVisibility(View.GONE);
            ivAlPay.setVisibility(View.GONE);
            ivWxPay.setVisibility(View.VISIBLE);
        });
        mHandler = new AlPayResultHandler(this);
        if (!LoginUtil.isLogin()) {
            llBalancePay.setVisibility(View.GONE);
        }
        payDialog = new BaseDialog(this);
        Window window = payDialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        payDialog.setContentView(R.layout.dialog_input_pay_key);
        tv1 = payDialog.findViewById(R.id.tv_num_1);
        tv2 = payDialog.findViewById(R.id.tv_num_2);
        tv3 = payDialog.findViewById(R.id.tv_num_3);
        tv4 = payDialog.findViewById(R.id.tv_num_4);
        tv5 = payDialog.findViewById(R.id.tv_num_5);
        tv6 = payDialog.findViewById(R.id.tv_num_6);
        tvList = new ArrayList<>();
        tvList.add(tv1);
        tvList.add(tv2);
        tvList.add(tv3);
        tvList.add(tv4);
        tvList.add(tv5);
        tvList.add(tv6);
        etNum = payDialog.findViewById(R.id.et_num);
        etNum.addTextChangedListener(this);
        payDialog.findViewById(R.id.img_cancel).setOnClickListener(v -> {
            payDialog.dismiss();
            etNum.setText("");
        });
        rulesDialog = new BaseDialog(this);
        Window w = rulesDialog.getWindow();
        w.requestFeature(Window.FEATURE_NO_TITLE);
        rulesDialog.setContentView(R.layout.dialog_recharge_rules);
        tvChargeRules = rulesDialog.findViewById(R.id.tv_charge_rules);
        rulesDialog.findViewById(R.id.tv_dismiss).setOnClickListener(v -> rulesDialog.dismiss());
    }

    private void getParkInfo() {
        getTime(Api.getDefaultService().getParkOrderInfo(unitId)
                , new RxObserver<BaseEntity<ParkInfoBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<ParkInfoBean> baseEntity) {
                        ParkInfoBean data = baseEntity.getData();
                        rechargeNum = data.getPay();
                        tvChargeNum.setText("￥" + data.getPay());
                        btnPay.setText("确认支付  ￥" + data.getPay());
                        String time = "停车时间：" + data.getStart_time() + "-" + data.getEnd_time() + "\n停车时长：" + data.getStay_time();
                        tvParkDuration.setText(time);
                        StringBuffer fee = new StringBuffer("收费标准：");
                        for (String s : data.getFee_desc()) {
                            fee.append(s).append("，");
                        }
                        fee.deleteCharAt(fee.lastIndexOf("，"));
                        fee.append("。");
                        tvChargeRules.setText(fee);
                        StringBuffer discount = new StringBuffer(data.getDiscount_desc());
                        discount.append("  -").append(data.getDiscount_money()).append("元。");
                        if (data.getDiscount_money() != 0f)
                            tvDiscount.setText(discount);
                    }

                    @Override
                    public void onError(String errMsg) {
                        super.onError(errMsg);
                        finish();
                    }
                });
    }

    @Override
    public void onCheckDoubleClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay:
                recharge();
                break;
            case R.id.iv_help:
                rulesDialog.show();
                break;
        }
    }

    private void recharge() {
        if (TextUtils.isEmpty(rechargeNum)) {
            return;
        }
        switch (payType) {
            case 1:
                getTime(Api.getDefaultService().alPay("付费", "停车付费", String.valueOf(rechargeNum)
                        , 4, payType, unitId, null), new RxObserver<BaseEntity<AlPayBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<AlPayBean> baseEntity) {
                        Runnable payRunnable = () -> {
                            String orderInfo = baseEntity.getData().getCallback();
                            PayTask aliPay = new PayTask(ParkChargeActivity.this);
                            Map<String, String> result = aliPay.payV2(orderInfo, true);
                            Message msg = new Message();
                            result.put("num", String.valueOf(rechargeNum));
                            result.put("from", "1");
                            msg.what = AlPayResultHandler.SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        };
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                });
                break;
            case 2:
                getTime(Api.getDefaultService().wxPay("付费", "停车付费", String.valueOf(rechargeNum)
                        , 4, payType, unitId, null), new RxObserver<BaseEntity<WxPayBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<WxPayBean> baseEntity) {
                        WXPayEntryActivity.setNum(String.valueOf(rechargeNum));
                        WXPayEntryActivity.setFrom(1);
                        WxPayBean.CallbackBean callback = baseEntity.getData().getCallback();
                        WxPayHelper.getInstance().WexPay(callback);
                        WXPayEntryActivity.setActivity(ParkChargeActivity.this);
                    }
                });
                break;
            case 4:
                if (CacheData.isFreePay() == 1) {
                    balancePay();
                } else {
                    payDialog.show();
                }
                break;
        }
    }

    private void balancePay() {
        getTime(Api.getDefaultService().balancePay("付费", "停车付费", String.valueOf(rechargeNum)
                , 4, payType, unitId, null), new RxObserver<BaseEntity>(this, true) {
            @Override
            public void onSuccess(BaseEntity baseEntity) {

            }
        });
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
            payKey = num;
            payDialog.dismiss();
            checkPayKey();
        }
    }

    private void checkPayKey() {
        String encrypt = AesUtil.encrypt(payKey);
        getTime(Api.getDefaultService().checkPayKey(encrypt)
                , new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        balancePay();
                    }
                });
    }
}
