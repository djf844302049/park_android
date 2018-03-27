package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.dialog.ConfirmCancelDialog;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.IntentKey;
import com.anyidc.cloudpark.utils.ToastUtil;

/**
 * 操作车位锁的界面
 * Created by Administrator on 2018/3/14.
 */

public class OptParkLockActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivUp, ivDown;
    private String parkNum = "";
    private int fromType = 2;
    public static int FROMSHAREOWNER = 1;
    public static int FROMMANAGER = 2;
    private ConfirmCancelDialog confirmCancelDialog;

    public static void start(Context context, String parkNum,int from) {
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
        parkNum = getIntent().getStringExtra(IntentKey.INTENT_KEY_STRING);
        fromType = getIntent().getIntExtra(IntentKey.INTENT_KEY_STRING,2);
        if (TextUtils.isEmpty(parkNum)) {
            finish();
            return;
        }
        ivUp = findViewById(R.id.iv_up);
        ivUp.setOnClickListener(this);
        ivDown = findViewById(R.id.iv_down);
        ivDown.setOnClickListener(this);
        initTitle("操作车位锁");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_up:
                showConfirmdialog(getString(R.string.up_lock_tip),"rise");
                break;
            case R.id.iv_down:
                showConfirmdialog(getString(R.string.down_lock_tip),"drop");
                break;
        }
    }

    private void showConfirmdialog(String content,final String opt){
        if(confirmCancelDialog == null){
            confirmCancelDialog = new ConfirmCancelDialog(this,"",content,getString(R.string.common_confirm),getString(R.string.common_cancel));
        }
        confirmCancelDialog.setContent(content);
        confirmCancelDialog.setClickListener(new ConfirmCancelDialog.ClickListener() {
            @Override
            public void confirm() {
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
        if(confirmCancelDialog != null){
            confirmCancelDialog.dismiss();
            confirmCancelDialog = null;
        }
    }
}
