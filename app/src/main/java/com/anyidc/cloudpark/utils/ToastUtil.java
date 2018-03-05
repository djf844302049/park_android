package com.anyidc.cloudpark.utils;

import android.view.View;
import android.widget.Toast;

import com.anyidc.cloudpark.BaseApplication;

/**
 * Created by Administrator on 2018/3/5.
 */

public class ToastUtil {
    private static Toast mToast = null;//全局唯一的Toast

    private ToastUtil() {

    }

    /**
     * 取消吐司
     */
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * 显示普通吐司通知
     *
     * @param message  吐司内容
     * @param duration 吐司时间
     */
    public static void showToast(String message, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.appContext, message, duration);
        } else {
            mToast.setText(message);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    /**
     * 显示自定义吐司通知
     *
     * @param message  吐司内容
     * @param duration 吐司时间
     * @param view     自定义的吐司界面
     * @param gravity  吐司位置
     * @param xOffset  x方向偏移量
     * @param yOffset  y方向偏移量
     * @param hMargin  横向间距
     * @param vMargin  纵向间距
     */
    public static void customToast(String message, int duration, View view
            , int gravity, int xOffset, int yOffset, float hMargin, float vMargin) {
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.appContext, message, duration);
        } else {
            mToast.setText(message);
            mToast.setDuration(duration);
        }
        if (view != null) {
            mToast.setView(view);
        }
        mToast.setMargin(hMargin, vMargin);
        mToast.setGravity(gravity, xOffset, yOffset);
        mToast.show();
    }
}
