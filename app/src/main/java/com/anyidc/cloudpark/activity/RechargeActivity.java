package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alipay.sdk.app.PayTask;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.RechargeAdapter;
import com.anyidc.cloudpark.moduel.AlPayBean;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.RechargeBean;
import com.anyidc.cloudpark.moduel.WxPayBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.AlPayResultHandler;
import com.anyidc.cloudpark.utils.WxPayHelper;
import com.anyidc.cloudpark.wxapi.WXPayEntryActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/15.
 */

public class RechargeActivity extends BaseActivity {
    private RecyclerView rlv;
    private RechargeAdapter adapter;
    private List<RechargeBean> list = new ArrayList<>();
    private ImageView ivAlPay, ivWxPay;
    private int prePosition;
    private int rechargeNum;
    private AlPayResultHandler mHandler;
    private WeakReference<Context> weakReference;
    private int[] nums = {10, 20, 30, 100, 200, 500};
    private int payType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initData() {
        initTitle("充值余额");
        rlv = findViewById(R.id.rlv_recharge);
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
        ivAlPay = findViewById(R.id.iv_al_pay);
        ivWxPay = findViewById(R.id.iv_wx_pay);
        for (int num : nums) {
            RechargeBean bean = new RechargeBean();
            bean.setNum(num);
            list.add(bean);
        }
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 3);
        rlv.setLayoutManager(manager);
        adapter = new RechargeAdapter(list);
        rlv.setAdapter(adapter);
        rlv.setHasFixedSize(true);
        adapter.setOnItemClickListener((view, position) -> {
            rechargeNum = list.get(position).getNum();
            list.get(prePosition).setCheck(false);
            list.get(position).setCheck(true);
            adapter.notifyItemChanged(position);
            adapter.notifyItemChanged(prePosition);
            prePosition = position;
        });
        weakReference = new WeakReference<>(this);
        mHandler = new AlPayResultHandler(weakReference.get());
    }

    @Override
    public void onCheckDoubleClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm_pay:
                recharge();
                break;
        }
    }

    private void recharge() {
        if (rechargeNum == 0) {
            return;
        }
        switch (payType) {
            case 1:
                getTime(Api.getDefaultService().alPay("充值", "余额充值", String.valueOf(0.01)
                        , 1, payType, null), new RxObserver<BaseEntity<AlPayBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<AlPayBean> baseEntity) {
                        Runnable payRunnable = () -> {
                            String orderInfo = baseEntity.getData().getCallback();
                            PayTask alipay = new PayTask(RechargeActivity.this);
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
                getTime(Api.getDefaultService().wxPay("充值", "余额充值", String.valueOf(0.01)
                        , 1, payType, null), new RxObserver<BaseEntity<WxPayBean>>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity<WxPayBean> baseEntity) {
                        WXPayEntryActivity.setNum(String.valueOf(rechargeNum));
                        WxPayBean.CallbackBean callback = baseEntity.getData().getCallback();
                        WxPayHelper.getInstance().WexPay(callback);
                    }
                });
                break;
        }
    }
}
