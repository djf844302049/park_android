package com.anyidc.cloudpark.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.anyidc.cloudpark.moduel.PayResult;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/11.
 */

public class AlPayResultHandler extends Handler {
    public static final int SDK_PAY_FLAG = 1;
    private Context context;

    public AlPayResultHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SDK_PAY_FLAG: {
                @SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                Log.e("tag", payResult.getMemo() + "-----------------" + payResult.getResult() + "------------" + payResult.getResultStatus());
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
}
