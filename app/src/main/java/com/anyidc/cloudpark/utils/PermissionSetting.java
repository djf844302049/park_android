package com.anyidc.cloudpark.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.anyidc.cloudpark.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.SettingService;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public final class PermissionSetting {

    private final Context mContext;

    public PermissionSetting(Context context) {
        this.mContext = context;
    }

    public void showSetting(final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(mContext, permissions);
        String message = mContext.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        if (AndPermission.hasAlwaysDeniedPermission(mContext, permissions)) {
            final SettingService service = AndPermission.permissionSetting(mContext);
            new AlertDialog.Builder(mContext)
                    .setTitle("提示")
                    .setCancelable(false)
                    .setMessage(message)
                    .setPositiveButton("确定", (dialog, which) -> service.execute())
                    .setNegativeButton("取消", (dialog, which) -> service.cancel())
                    .show();
        }
    }
}
