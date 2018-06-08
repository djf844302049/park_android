package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.ReasonsAdapter;
import com.anyidc.cloudpark.dialog.ConfirmCancelDialog;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.IntentKey;
import com.anyidc.cloudpark.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作车位锁的界面
 * Created by Administrator on 2018/3/14.
 */

public class OptParkLockActivity extends BaseActivity {
    private ImageView ivUp, ivDown;
    private String parkNum = "";
    private int fromType = 2;
    public static int FROMSHAREOWNER = 1;
    public static int FROMMANAGER = 2;
    private ConfirmCancelDialog confirmCancelDialog;
    private BottomSheetDialog bottomSheetDialog;
    private TextView tvHead;
    private RecyclerView rlvReason;
    private List<String> reasons = new ArrayList<>();
    private ReasonsAdapter adapter;
    private String opt;

    public static void start(Context context, String parkNum, int from) {
        Intent intent = new Intent(context, OptParkLockActivity.class);
        intent.putExtra(IntentKey.INTENT_KEY_STRING, parkNum);
        intent.putExtra(IntentKey.INTENT_KEY_INT, from);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_opt_park_lock;
    }

    @Override
    protected void initData() {
        reasons.add("缴费异常");
        reasons.add("设备故障");
        reasons.add("军警等公务车辆");
        reasons.add("巡检设备短暂停留");
        adapter = new ReasonsAdapter(reasons);
        bottomSheetDialog = new BottomSheetDialog(this, R.style.dialog);
        bottomSheetDialog.setContentView(R.layout.layout_bottom_choice);
        rlvReason = (RecyclerView) bottomSheetDialog.findViewById(R.id.rlv_bottom_choice);
        rlvReason.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rlvReason.setAdapter(adapter);
        tvHead = (TextView) bottomSheetDialog.findViewById(R.id.tv_ahead);
        TextView tvBottom = (TextView) bottomSheetDialog.findViewById(R.id.tv_bottom);
        tvBottom.setText("其他原因");
        tvBottom.setOnClickListener(v -> {
            showConfirmdialog("", opt);
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> bottomSheetDialog.dismiss());
        tvHead.setText("请选择升起原因");
        parkNum = getIntent().getStringExtra(IntentKey.INTENT_KEY_STRING);
        fromType = getIntent().getIntExtra(IntentKey.INTENT_KEY_STRING, 2);
        if (TextUtils.isEmpty(parkNum)) {
            finish();
            return;
        }
        ivUp = findViewById(R.id.iv_up);
        ivUp.setOnClickListener(clickListener);
        ivDown = findViewById(R.id.iv_down);
        ivDown.setOnClickListener(clickListener);
        initTitle("操作车位锁");

    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.iv_up:
                opt = "rise";
                tvHead.setText("请选择升起原因");
                bottomSheetDialog.show();
//                showConfirmdialog(getString(R.string.up_lock_tip), "rise");
                break;
            case R.id.iv_down:
                opt = "drop";
                tvHead.setText("请选择降下原因");
                bottomSheetDialog.show();
//                showConfirmdialog(getString(R.string.down_lock_tip), "drop");
                break;
        }
    }

    private void showConfirmdialog(String content, final String opt) {
        if (confirmCancelDialog == null) {
            confirmCancelDialog = new ConfirmCancelDialog(this, "请输入操作原因", "", getString(R.string.common_confirm), getString(R.string.common_cancel));
            confirmCancelDialog.setEtVisibility(View.VISIBLE);
        }
//        confirmCancelDialog.setContent(content);
        confirmCancelDialog.setClickListener(new ConfirmCancelDialog.ClickListener() {
            @Override
            public void confirm() {
                if (TextUtils.isEmpty(confirmCancelDialog.getEtContent())) {
                    return;
                }
                optUpDown(opt);
                confirmCancelDialog.dismiss();
            }

            @Override
            public void cancel() {
                confirmCancelDialog.dismiss();
            }
        });
        confirmCancelDialog.show();
    }

    /**
     * 车位锁上升和下降
     * rise 上升 drop 下降
     *
     * @param opt
     */
    private void optUpDown(String opt) {
        getTime(Api.getDefaultService().parkingControl(parkNum, opt)
                , new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        ToastUtil.showToast("操作成功", Toast.LENGTH_SHORT);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (confirmCancelDialog != null) {
            confirmCancelDialog.dismiss();
            confirmCancelDialog = null;
        }
    }
}
