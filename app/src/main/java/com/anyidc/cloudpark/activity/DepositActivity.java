package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.AlPayBean;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.WxPayBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.AlPayResultHandler;
import com.anyidc.cloudpark.utils.WxPayHelper;
import com.anyidc.cloudpark.wxapi.WXPayEntryActivity;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/15.
 */

public class DepositActivity extends BaseActivity {

    private ImageView ivAlPay, ivWxPay;
    private TextView tvDepositNum;
    private AlPayResultHandler mHandler;
    private int payType;

    public static void actionStart(Context context, float depositNum) {
        Intent intent = new Intent(context, DepositActivity.class);
        intent.putExtra("depositNum", depositNum);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge_deposit;
    }

    @Override
    protected void initData() {
        initTitle("缴纳押金");
        float depositNum = getIntent().getFloatExtra("depositNum", 0f);
        mHandler = new AlPayResultHandler(this);
        ivAlPay = findViewById(R.id.iv_al_pay);
        ivWxPay = findViewById(R.id.iv_wx_pay);
        tvDepositNum = findViewById(R.id.tv_deposit_num);
        tvDepositNum.setText("￥" + depositNum);
        findViewById(R.id.ll_al_pay).setOnClickListener(v -> {
            ivAlPay.setVisibility(View.VISIBLE);
            ivWxPay.setVisibility(View.GONE);
            payType = 1;
        });
        findViewById(R.id.ll_wx_pay).setOnClickListener(v -> {
            ivAlPay.setVisibility(View.GONE);
            ivWxPay.setVisibility(View.VISIBLE);
            payType = 2;
        });
        findViewById(R.id.btn_confirm_pay).setOnClickListener(clickListener);
    }

    private void recharge() {
        switch (payType) {
            case 1:
                getTime(Api.getDefaultService().alPay("缴纳押金", "缴纳押金", String.valueOf(0.01)
                        , 2, payType, null, null), new RxObserver<BaseEntity<AlPayBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<AlPayBean> baseEntity) {
                        Runnable payRunnable = () -> {
                            String orderInfo = baseEntity.getData().getCallback();
                            PayTask alipay = new PayTask(DepositActivity.this);
                            Map<String, String> result = alipay.payV2(orderInfo, true);
                            Message msg = new Message();
                            result.put("num", String.valueOf(0.01));
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
                getTime(Api.getDefaultService().wxPay("缴纳押金", "缴纳押金", String.valueOf(0.01)
                        , 2, payType, null, null), new RxObserver<BaseEntity<WxPayBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<WxPayBean> baseEntity) {
                        WXPayEntryActivity.setNum(String.valueOf(0.01));
                        WxPayBean.CallbackBean callback = baseEntity.getData().getCallback();
                        WxPayHelper.getInstance().WexPay(callback);
                        WXPayEntryActivity.setActivity(DepositActivity.this);
                    }
                });
                break;
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        recharge();
    }
}
