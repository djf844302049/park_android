package com.anyidc.cloudpark.utils;

import android.widget.Toast;

import com.anyidc.cloudpark.BaseApplication;
import com.anyidc.cloudpark.moduel.WxPayBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2017/5/23.
 * 支付类
 */

public class WxPayHelper {

    private static WxPayHelper mPayHelper = null;

    public static WxPayHelper getInstance() {
        if (mPayHelper == null) {
            mPayHelper = new WxPayHelper();
        }
        return mPayHelper;
    }

    public WxPayHelper() {

    }

    IWXAPI msgApi = null;

    public void WexPay(WxPayBean.CallbackBean info) {
        if (msgApi == null) {
            msgApi = WXAPIFactory.createWXAPI(BaseApplication.appContext, null);
            msgApi.registerApp(info.getAppid());// 将该app注册到微信
        }
        PayReq req = new PayReq();
        if (!msgApi.isWXAppInstalled()) {
            ToastUtil.showToast("手机中没有安装微信客户端!", Toast.LENGTH_SHORT);
            return;
        }
        if (info != null) {
            req.appId = info.getAppid();
            req.partnerId = info.getPartnerid();
            req.prepayId = info.getPrepayid();
            req.nonceStr = info.getNoncestr();
            req.timeStamp = info.getTimestamp();
            req.packageValue = info.getPackageX();
            req.sign = info.getSign();
            msgApi.sendReq(req);
        }
    }
}
