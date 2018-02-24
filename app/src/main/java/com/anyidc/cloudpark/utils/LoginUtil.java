package com.anyidc.cloudpark.utils;

import android.text.TextUtils;

/**
 * Created by Administrator on 2018/2/24.
 */

public class LoginUtil {

    public static boolean isLogin() {
        String token = (String) SpUtils.get(SpUtils.TOKEN, "");
        return !TextUtils.isEmpty(token);
    }

    public static void logout() {
        SpUtils.set(SpUtils.TOKEN, null);
        SpUtils.set(SpUtils.USERINFO, null);
    }
}
