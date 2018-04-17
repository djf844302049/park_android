package com.anyidc.cloudpark.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.anyidc.cloudpark.activity.PayResultActivity;
import com.anyidc.cloudpark.moduel.PayResult;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/11.
 */

public class AlPayResultHandler extends Handler {
    public static final int SDK_PAY_FLAG = 1;
    private WeakReference<Context> context;

    public AlPayResultHandler(Context context) {
        this.context = new WeakReference<>(context);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SDK_PAY_FLAG: {
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    PayResultActivity.actionStart(context.get(), 1, payResult.getNum());
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    PayResultActivity.actionStart(context.get(), 2, payResult.getNum());
                }
                break;
            }
        }
    }
}
