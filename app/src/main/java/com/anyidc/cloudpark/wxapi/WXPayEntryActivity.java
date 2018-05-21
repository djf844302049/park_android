package com.anyidc.cloudpark.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.anyidc.cloudpark.activity.PayResultActivity;
import com.anyidc.cloudpark.utils.WxPayHelper;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static String num;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (WxPayHelper.msgApi == null) {
            return;
        }
        WxPayHelper.msgApi.handleIntent(getIntent(), this);
    }

    public static void setNum(String nums) {
        num = nums;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WxPayHelper.msgApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case 0:
                PayResultActivity.actionStart(this, 1, num);
                break;
            case -1://失败
            case -2://取消
                PayResultActivity.actionStart(this, 2, null);
                break;
        }
        finish();
    }
}