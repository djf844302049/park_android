package com.anyidc.cloudpark.utils;

import com.anyidc.cloudpark.moduel.InfoBean;

/**
 * Created by Administrator on 2018/2/26.
 */

public class CacheData {
    private static InfoBean infoBean = null;

    public static void setInfoBean(InfoBean newInfoBean) {
        infoBean = newInfoBean;
        SpUtils.setObject(SpUtils.USERINFO, infoBean);
    }

    public static int getUser_id() {
        if (infoBean == null) {
            infoBean = SpUtils.getObject(SpUtils.USERINFO, InfoBean.class);
        }
        if (infoBean == null) {
            return -1;
        }
        return infoBean.getUser_id();
    }

    public static String getUserName() {
        if (infoBean == null) {
            infoBean = SpUtils.getObject(SpUtils.USERINFO, InfoBean.class);
        }
        if (infoBean == null) {
            return "";
        }
        return infoBean.getUsername();
    }

    public static String getHeader_img() {
        if (infoBean == null) {
            infoBean = SpUtils.getObject(SpUtils.USERINFO, InfoBean.class);
        }
        if (infoBean == null) {
            return "";
        }
        return infoBean.getHeader_img();
    }


    public static int getSex() {
        if (infoBean == null) {
            infoBean = SpUtils.getObject(SpUtils.USERINFO, InfoBean.class);
        }
        if (infoBean == null) {
            return -1;
        }
        return infoBean.getSex();
    }

}
