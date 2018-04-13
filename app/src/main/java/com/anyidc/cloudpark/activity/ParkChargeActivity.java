package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.AlPayBean;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.ParkInfoBean;
import com.anyidc.cloudpark.moduel.WxPayBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.AlPayResultHandler;
import com.anyidc.cloudpark.utils.WxPayHelper;
import com.anyidc.cloudpark.wxapi.WXPayEntryActivity;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/16.
 */

public class ParkChargeActivity extends BaseActivity {
    private TextView tvChargeNum, tvParkDuration, tvSharePark, tvDiscount;
    private ImageView ivHelp, ivBalancePay, ivAlPay, ivWxPay;
    private String unitId;
    private AlPayResultHandler mHandler;
    private WeakReference<Context> weakReference;
    private String rechargeNum;
    private int payType;

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
        ivAlPay = findViewById(R.id.iv_al_pay);
        ivWxPay = findViewById(R.id.iv_wx_pay);
        ivBalancePay = findViewById(R.id.iv_balance_pay);
        findViewById(R.id.btn_pay).setOnClickListener(clickListener);
        findViewById(R.id.ll_balance_pay).setOnClickListener(v -> {
            ivBalancePay.setImageResource(R.drawable.ic_checked);
            ivAlPay.setImageResource(R.drawable.ic_message_check);
            ivWxPay.setImageResource(R.drawable.ic_message_check);
            payType = 4;
        });
        findViewById(R.id.ll_al_pay).setOnClickListener(v -> {
            payType = 1;
            ivBalancePay.setImageResource(R.drawable.ic_message_check);
            ivAlPay.setImageResource(R.drawable.ic_checked);
            ivWxPay.setImageResource(R.drawable.ic_message_check);
        });
        findViewById(R.id.ll_wx_pay).setOnClickListener(v -> {
            payType = 2;
            ivBalancePay.setImageResource(R.drawable.ic_message_check);
            ivAlPay.setImageResource(R.drawable.ic_message_check);
            ivWxPay.setImageResource(R.drawable.ic_checked);
        });
        weakReference = new WeakReference<>(this);
        mHandler = new AlPayResultHandler(weakReference.get());
    }

    private void getParkInfo() {
        getTime(Api.getDefaultService().getParkOrderInfo(unitId)
                , new RxObserver<BaseEntity<ParkInfoBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<ParkInfoBean> baseEntity) {
                        ParkInfoBean data = baseEntity.getData();
                        rechargeNum = data.getPay();
                        tvChargeNum.setText("￥" + data.getPay());
                        String time = "停车时间：" + data.getOrder().getCreate_time() + "-" + data.getNow() + "    停车时长：" + data.getDuration();
                        tvParkDuration.setText(time);
                    }
                });
    }

    @Override
    public void onCheckDoubleClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay:
                recharge();
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
                        , 4, payType, unitId), new RxObserver<BaseEntity<AlPayBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<AlPayBean> baseEntity) {
                        Runnable payRunnable = () -> {
                            String orderInfo = baseEntity.getData().getCallback();
                            PayTask alipay = new PayTask(ParkChargeActivity.this);
                            Map<String, String> result = alipay.payV2(orderInfo, true);
                            Message msg = new Message();
                            result.put("num", String.valueOf(rechargeNum));
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
                        , 4, payType, unitId), new RxObserver<BaseEntity<WxPayBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<WxPayBean> baseEntity) {
                        WXPayEntryActivity.setNum(String.valueOf(rechargeNum));
                        WxPayBean.CallbackBean callback = baseEntity.getData().getCallback();
                        WxPayHelper.getInstance().WexPay(callback);
                    }
                });
                break;
            case 4:
                getTime(Api.getDefaultService().balancePay("付费", "停车付费", String.valueOf(rechargeNum)
                        , 4, payType, unitId), new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {

                    }
                });
                break;
        }
    }
}
