package com.anyidc.cloudpark.moduel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/4/12.
 */

public class WxPayBean {


    /**
     * callback : {"appid":"wx8ba55aca60034fdf","partnerid":"1501864681","prepayid":"wx12102005056010d076dad5150746204157","package":"Sign=WXPay","noncestr":"8m89od1073e7a6q1eo3nwpmx4ar3zpbz","timestamp":1523499605,"sign":"72932FC6455C7EF22ACA29BFDFF8A4C3"}
     */

    private CallbackBean callback;

    public CallbackBean getCallback() {
        return callback;
    }

    public void setCallback(CallbackBean callback) {
        this.callback = callback;
    }

    public static class CallbackBean {
        /**
         * appid : wx8ba55aca60034fdf
         * partnerid : 1501864681
         * prepayid : wx12102005056010d076dad5150746204157
         * package : Sign=WXPay
         * noncestr : 8m89od1073e7a6q1eo3nwpmx4ar3zpbz
         * timestamp : 1523499605
         * sign : 72932FC6455C7EF22ACA29BFDFF8A4C3
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
