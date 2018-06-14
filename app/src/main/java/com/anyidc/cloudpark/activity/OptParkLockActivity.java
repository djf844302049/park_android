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
import com.anyidc.cloudpark.moduel.OptReasonBean;
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
    private int fromType = 1;
    public static final int FROMMANAGER = 1;
    public static final int FROMSHAREOWNER = 2;
    private ConfirmCancelDialog confirmCancelDialog;
    private BottomSheetDialog bottomSheetDialog;
    private TextView tvHead;
    private RecyclerView rlvReason;
    private List<OptReasonBean> reasons = new ArrayList<>();
    private ReasonsAdapter adapter;
    private String opt;
    private String reasonCode;
    private String reasonNote;

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
            showConfirmdialog();
            bottomSheetDialog.dismiss();
        });
        adapter.setOnItemClickListener((view, position) -> {
            reasonCode = String.valueOf(reasons.get(position).getCode());
            reasonNote = null;
            optUpDown();
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> bottomSheetDialog.dismiss());
        tvHead.setText("请选择升起原因");
        parkNum = getIntent().getStringExtra(IntentKey.INTENT_KEY_STRING);
        fromType = getIntent().getIntExtra(IntentKey.INTENT_KEY_INT, 2);
        getOptReasons();
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

                break;
            case R.id.iv_down:
                opt = "drop";
                tvHead.setText("请选择降下原因");
                bottomSheetDialog.show();
                break;
        }
    }

    private void showConfirmdialog() {
        confirmCancelDialog.setClickListener(new ConfirmCancelDialog.ClickListener() {
            @Override
            public void confirm() {
                if (TextUtils.isEmpty(confirmCancelDialog.getEtContent())) {
                    return;
                }
                reasonNote = confirmCancelDialog.getEtContent();
                confirmCancelDialog.clearEtContent();
                confirmCancelDialog.dismiss();
                optUpDown();
            }

            @Override
            public void cancel() {
                confirmCancelDialog.clearEtContent();
                confirmCancelDialog.dismiss();
            }
        });
        confirmCancelDialog.show();
    }

    /**
     * 车位锁上升和下降
     * rise 上升 drop 下降
     */
    private void optUpDown() {
        getTime(Api.getDefaultService().parkingControl(parkNum, opt, reasonCode, reasonNote, fromType)
                , new RxObserver<BaseEntity>(this, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        ToastUtil.showToast(baseEntity.getMessage(), Toast.LENGTH_SHORT);
                    }
                });
    }

    private void getOptReasons() {
        getTime(Api.getDefaultService().optReason(fromType), new RxObserver<BaseEntity<List<OptReasonBean>>>(this, true) {
            @Override
            public void onSuccess(BaseEntity<List<OptReasonBean>> baseEntity) {
                List<OptReasonBean> data = baseEntity.getData();
                if (data != null && data.size() > 0) {
                    reasons.addAll(data);
                    for (OptReasonBean reason : reasons) {
                        if (reason.getCode() == 201) {
                            reasons.remove(reason);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
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
