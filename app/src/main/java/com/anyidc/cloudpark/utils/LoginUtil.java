package com.anyidc.cloudpark.utils;

/**
 * Created by Administrator on 2018/2/24.
 */

public class LoginUtil {

    public static boolean isLogin() {
        return CacheData.getInfoBean()!=null;
    }

    public static void logout() {
        CacheData.setInfoBean(null);
    }
}
