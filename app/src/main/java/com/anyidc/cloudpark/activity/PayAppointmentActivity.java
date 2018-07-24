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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.dialog.BaseDialog;
import com.anyidc.cloudpark.moduel.AlPayBean;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.WxPayBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.AesUtil;
import com.anyidc.cloudpark.utils.AlPayResultHandler;
import com.anyidc.cloudpark.utils.CacheData;
import com.anyidc.cloudpark.utils.CheckDoubleClickListener;
import com.anyidc.cloudpark.utils.FiveMinuCountDownRunnable;
import com.anyidc.cloudpark.utils.WxPayHelper;
import com.anyidc.cloudpark.wxapi.WXPayEntryActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by linwenxiong on 2018/4/17.
 */

public class PayAppointmentActivity extends BaseActivity implements TextWatcher {

    private TextView tvTitle, tvUnitNum, tvShareTime;
    private LinearLayout llShareTip, llShareTime;
    private TextView tvThirty, tvSixty;
    private TextView tvPayTime, tvShareFee, tvBalanceNum;
    private RadioButton rbAccountPay, rbZFBPay, rbWXPay;
    private String parkName, unitNum, shareTime, shareFee;
    private AlPayResultHandler mHandler;
    protected CheckDoubleClickListener clickListener;
    private float payNum;
    private float orderNum;
    private Button btnPay;
    private int payType = 4;//1支付宝 2微信 3银联 4账号
    private String carId;
    private BaseDialog payDialog;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private EditText etNum;
    private List<TextView> tvList;
    private String payKey;
    private String balanceNum;

    public static void start(Context context, String parkName, String unitNum, String shareTime,
                             float payNum, String carId, String shareFee, String balanceNum) {
        Intent intent = new Intent(context, PayAppointmentActivity.class);
        intent.putExtra("parkName", parkName);
        intent.putExtra("unitNum", unitNum);
        intent.putExtra("shareTime", shareTime);
        intent.putExtra("shareFee", shareFee);
        intent.putExtra("payNum", payNum);
        intent.putExtra("balanceNum", balanceNum);
        intent.putExtra("carId", carId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_park;
    }

    @Override
    protected void initData() {
        initTitle("预约停车");
        btnPay = findViewById(R.id.btn_pay);
        tvTitle = findViewById(R.id.tv_park_name);
        tvUnitNum = findViewById(R.id.tv_unit_num);
        tvShareTime = findViewById(R.id.tv_share_time);
        tvShareFee = findViewById(R.id.tv_share_fee);
        tvBalanceNum = findViewById(R.id.tv_balance_num);
        llShareTip = findViewById(R.id.ll_share_tip);
        llShareTime = findViewById(R.id.ll_share_time);
        rbAccountPay = findViewById(R.id.rb_account_pay);
        rbZFBPay = findViewById(R.id.rb_zhifubao_pay);
        rbWXPay = findViewById(R.id.rb_weixin_pay);
//        rbYLPay = findViewById(R.id.rb_yinlian_pay);
        tvPayTime = findViewById(R.id.tv_pay_time);
        String thirty = new SimpleDateFormat("HH:mm").format(new Date().getTime() + 30 * 60 * 1000);
        String sixty = new SimpleDateFormat("HH:mm").format(new Date().getTime() + 60 * 60 * 1000);
        tvThirty = findViewById(R.id.tv_thirty_minute);
        tvThirty.setText(thirty);
        tvSixty = findViewById(R.id.tv_sixty_minute);
        tvSixty.setText(sixty);
        tvSixty.setSelected(true);
        tvThirty.setOnClickListener(v -> {
            tvSixty.setSelected(false);
            tvThirty.setSelected(true);
            orderNum = payNum / 2;
            btnPay.setText("确认支付￥" + orderNum);
        });
        tvSixty.setOnClickListener(v -> {
            tvSixty.setSelected(true);
            tvThirty.setSelected(false);
            orderNum = payNum;
            btnPay.setText("确认支付￥" + orderNum);
        });
        rbAccountPay.setChecked(true);
        findViewById(R.id.rl_balance_pay).setOnClickListener(v -> {
            payType = 4;
            rbAccountPay.setChecked(true);
            rbZFBPay.setChecked(false);
            rbWXPay.setChecked(false);
        });
        findViewById(R.id.rl_al_pay).setOnClickListener(v -> {
            payType = 1;
            rbAccountPay.setChecked(false);
            rbZFBPay.setChecked(true);
            rbWXPay.setChecked(false);
        });
        findViewById(R.id.rl_wx_pay).setOnClickListener(v -> {
            payType = 2;
            rbAccountPay.setChecked(false);
            rbZFBPay.setChecked(false);
            rbWXPay.setChecked(true);
        });
        clickListener = new CheckDoubleClickListener(this);
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
        btnPay.setOnClickListener(clickListener);
        updateView();
        mHandler = new AlPayResultHandler(this);
    }

    private void updateView() {
        Intent intent = getIntent();
        parkName = intent.getStringExtra("parkName");
        unitNum = intent.getStringExtra("unitNum");
        shareTime = intent.getStringExtra("shareTime");
        shareFee = intent.getStringExtra("shareFee");
        balanceNum = intent.getStringExtra("balanceNum");
        payNum = intent.getFloatExtra("payNum", 0f);
        orderNum = payNum;
        carId = intent.getStringExtra("carId");
        if (TextUtils.isEmpty(shareTime)) {
            llShareTime.setVisibility(View.GONE);
            llShareTip.setVisibility(View.GONE);
//            getNormalParkDetail();
        } else {
            llShareTime.setVisibility(View.VISIBLE);
            llShareTip.setVisibility(View.VISIBLE);
//            getShareParkDetail();
        }
        tvBalanceNum.setText("账户余额为" + balanceNum + "可使用");
        tvTitle.setText(parkName);
        tvUnitNum.setText(unitNum);
        tvShareTime.setText(shareTime);
        tvShareFee.setText(shareFee);
        btnPay.setText("确认支付￥" + payNum);
        btnPay.setEnabled(true);
        new FiveMinuCountDownRunnable(tvPayTime, btnPay).run();
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pay:
                toPay();
                break;
        }
    }

    /**
     * 支付
     */
    private void toPay() {
        switch (payType) {
            case 1:
                alPay();
                break;
            case 2:
                wxPay();
                break;
            case 3:
                break;
            case 4:
                if (CacheData.isFreePay() == 1) {//开启小额免密支付
                    balancePay();
                } else {
                    etNum.setText("");
                    payDialog.show();
                }
                break;
        }
    }

    private void wxPay() {
        getTime(Api.getDefaultService().wxPay("预约", "预约付款", String.valueOf(orderNum)
                , 3, payType, unitNum, carId), new RxObserver<BaseEntity<WxPayBean>>(this, true) {
            @Override
            public void onSuccess(BaseEntity<WxPayBean> baseEntity) {
                WXPayEntryActivity.setNum(String.valueOf(orderNum));
                WXPayEntryActivity.setFrom(2);
                WxPayBean.CallbackBean callback = baseEntity.getData().getCallback();
                WxPayHelper.getInstance().WexPay(callback);
                WXPayEntryActivity.setActivity(PayAppointmentActivity.this);
            }
        });
    }

    private void alPay() {
        getTime(Api.getDefaultService().alPay("预约", "预约付款", String.valueOf(orderNum)
                , 3, payType, unitNum, carId), new RxObserver<BaseEntity<AlPayBean>>(this, true) {
            @Override
            public void onSuccess(BaseEntity<AlPayBean> baseEntity) {
                Runnable payRunnable = () -> {
                    String orderInfo = baseEntity.getData().getCallback();
                    PayTask alipay = new PayTask(PayAppointmentActivity.this);
                    Map<String, String> result = alipay.payV2(orderInfo, true);
                    Message msg = new Message();
                    result.put("num", String.valueOf(orderNum));
                    result.put("from", "2");
                    msg.what = AlPayResultHandler.SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
        });
    }

    private void balancePay() {
        getTime(Api.getDefaultService().balancePay("预约", "预约付款", String.valueOf(orderNum)
                , 3, payType, unitNum, carId), new RxObserver<BaseEntity>(this, true) {
            @Override
            public void onSuccess(BaseEntity baseEntity) {
                PayResultActivity.actionStart(PayAppointmentActivity.this, 1, String.valueOf(orderNum), 2);
                PayAppointmentActivity.this.finish();
            }

            @Override
            public void onError(String errMsg) {
                PayResultActivity.actionStart(PayAppointmentActivity.this, 2, String.valueOf(orderNum), 2);
            }
        });
    }

    private void getNormalParkDetail() {
        getTime(Api.getDefaultService().normalParkDetail(unitNum), new RxObserver<BaseEntity>(this, true) {
            @Override
            public void onSuccess(BaseEntity baseEntity) {

            }
        });
    }

    private void getShareParkDetail() {
        getTime(Api.getDefaultService().shareParkDetail(unitNum), new RxObserver<BaseEntity>(this, true) {
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
