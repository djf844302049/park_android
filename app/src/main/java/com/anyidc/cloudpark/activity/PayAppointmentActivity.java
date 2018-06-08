package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.dialog.SelectDialog;
import com.anyidc.cloudpark.moduel.AlPayBean;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.WxPayBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.AlPayResultHandler;
import com.anyidc.cloudpark.utils.CheckDoubleClickListener;
import com.anyidc.cloudpark.utils.FiveMinuCountDownRunnable;
import com.anyidc.cloudpark.utils.WxPayHelper;
import com.anyidc.cloudpark.wxapi.WXPayEntryActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by linwenxiong on 2018/4/17.
 */

public class PayAppointmentActivity extends BaseActivity {

    private TextView tvTitle, tvUnitNum, tvShareTime;
    private LinearLayout llShareTip, llShareTime;
    private TextView tvThirty, tvSixty;
    private TextView tvPayTime;
    private RadioButton rbAccountPay, rbZFBPay, rbWXPay, rbYLPay;
    private String parkName, unitNum, shareTime;
    private SelectDialog selectDialog;
    private AlPayResultHandler mHandler;
    protected CheckDoubleClickListener clickListener;
    private int payNum;
    private int orderNum;
    private Button btnPay;
    private int payType = 0;//1支付宝 2微信 3银联 4账号

    public static void start(Context context, String parkName, String unitNum, String shareTime, int payNum) {
        Intent intent = new Intent(context, PayAppointmentActivity.class);
        intent.putExtra("parkName", parkName);
        intent.putExtra("unitNum", unitNum);
        intent.putExtra("shareTime", shareTime);
        intent.putExtra("payNum", payNum);
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
//        rbAccountPay.setOnClickListener(clickListener);
//        rbZFBPay.setOnClickListener(clickListener);
//        rbWXPay.setOnClickListener(clickListener);
//        rbYLPay.setOnClickListener(clickListener);
        btnPay.setOnClickListener(clickListener);
        updateView();
        mHandler = new AlPayResultHandler(this);
    }

    private void updateView() {
        Intent intent = getIntent();
        parkName = intent.getStringExtra("parkName");
        unitNum = intent.getStringExtra("unitNum");
        shareTime = intent.getStringExtra("shareTime");
        payNum = intent.getIntExtra("payNum", 0);
        if (TextUtils.isEmpty(shareTime)) {
            llShareTime.setVisibility(View.GONE);
            llShareTip.setVisibility(View.GONE);
        } else {
            llShareTime.setVisibility(View.VISIBLE);
            llShareTip.setVisibility(View.VISIBLE);
        }
        tvTitle.setText(parkName);
        tvUnitNum.setText(unitNum);
        tvShareTime.setText(shareTime);
        btnPay.setText("确认支付￥" + payNum);
        btnPay.setEnabled(true);
        new FiveMinuCountDownRunnable(tvPayTime, btnPay).run();
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
//            case R.id.rb_account_pay:
//                payType = 4;
//                break;
//            case R.id.rb_zhifubao_pay:
//                payType = 1;
//                break;
//            case R.id.rb_weixin_pay:
//                payType = 2;
//                break;
//            case R.id.rb_yinlian_pay:
//                payType = 3;
//                break;
//            case R.id.tv_appointment_time:
//                showTimeDialog();
//                break;
            case R.id.btn_pay:
                toPay();
                break;
        }
    }

    /**
     * 支付
     */
    private void toPay() {
        Log.e("tag", "点击了啊.....");
        switch (payType) {
            case 1:
                getTime(Api.getDefaultService().alPay("预约", "预约付款", String.valueOf(orderNum)
                        , 3, payType, unitNum), new RxObserver<BaseEntity<AlPayBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<AlPayBean> baseEntity) {
                        Runnable payRunnable = () -> {
                            String orderInfo = baseEntity.getData().getCallback();
                            PayTask alipay = new PayTask(PayAppointmentActivity.this);
                            Map<String, String> result = alipay.payV2(orderInfo, true);
                            Message msg = new Message();
                            result.put("num", String.valueOf(payNum));
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
                getTime(Api.getDefaultService().wxPay("预约", "预约付款", String.valueOf(payNum)
                        , 3, payType, unitNum), new RxObserver<BaseEntity<WxPayBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<WxPayBean> baseEntity) {
                        WXPayEntryActivity.setNum(String.valueOf(payNum));
                        WxPayBean.CallbackBean callback = baseEntity.getData().getCallback();
                        WxPayHelper.getInstance().WexPay(callback);
                    }
                });
                break;
            case 3:
                break;
            case 4:
                balancePay();
                break;
        }
    }

    private void balancePay() {
        getTime(Api.getDefaultService().balancePay("预约", "预约付款", String.valueOf(payNum)
                , 3, payType, unitNum), new RxObserver<BaseEntity>(this, true) {
            @Override
            public void onSuccess(BaseEntity baseEntity) {

            }
        });
    }

    private void showTimeDialog() {
        if (selectDialog == null) {
            selectDialog = new SelectDialog(this);
            selectDialog.setOnItemSelectListener(new SelectDialog.OnItemSelectListener() {
                @Override
                public void itemSelectListener(int position, String name) {
                    if (position == 0) {
                        payNum = 5;
                    } else {
                        payNum = 10;
                    }
                }
            });
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String[] items = new String[2];
        items[0] = sdf.format(System.currentTimeMillis() + 1800000);
        items[1] = sdf.format(System.currentTimeMillis() + 3600000);
        selectDialog.setItem(items);
        selectDialog.show();
    }
}
