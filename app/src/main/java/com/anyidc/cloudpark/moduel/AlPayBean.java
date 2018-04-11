package com.anyidc.cloudpark.moduel;

import com.anyidc.cloudpark.utils.SignUtils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/10.
 */

public class AlPayBean {


    /**
     * callback : {"app_id":"2088921884613118","biz_content":{"body":"余额充值","subject":"充值","out_trade_no":"2018041010260223650","total_amount":"10","product_code":"QUICK_MSECURITY_PAY","goods_type":"1"},"charset":"UTF-8","format":"JSON","method":"alipay.trade.app.pay","notify_url":"http://park.deyuelou.top/api/v1.notify/alipay","sign_type":"RSA2","timestamp":"2018-04-10 10:26:02","version":"1.0","sign":"1jHuqDsAjBBcV/+NSBK/d5PmwJsI1JqTaiV5x/2F6wPBCTCQIdpEohpH/z+py8AABhXl8Hu+FnOkEkhCM74VUTVHjDSHqTEpp4IsGHQhAwqUVqr2b8qzWr86CGwRgXCSnlG5qtYa//8k6731SRjYiRdzE0MqjDUgDCDG4c3wUn0vjHW+KVko/0+qC3VmUjr8rZ214wTG3MzWEmBGQqSEzMeaRODYj26U/4sOAvau5FdJhKjia1E+1fL42/4u4Um98+xsqplOS/vLiz9OVaGU1bifaHM7cf6KoJLzxFUmguFt/uj2aijGkwTQoEULO2Bf5x6b8Szq82ZJAsT9xRL7MQ=="}
     */

    private CallbackBean callback;
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCVF9avSru0TxpGDtjUb9jrumyjl0fwEDyYbqYPtnLCcmYSAbNDmj8pvWQL9JRRhu8gU/9ZuryKGX4YFkS8U8kBr8Ri3OwbQOkhebFKWGfZ5b1xiOtZL3jnCvQMM3FHLEgUjEwPWBPfksRCWBiOA17+fQ3FBGxaawb70KON9n70JNcP8VFsZnILeFpc1Ctcj3TOqgh8MBuL5KnW0czJM9iyov6786kdAaZ2Fn6gtMXSN92KFpUPlr0qtsO2FQKueNsRTlnMy3mNRrReP+SJWXcwiBIwxzbhql/arBiv7FcoUHOWDSScOPbO893XSX5AELJZvcshgH9nhpaAnPbkB7/1AgMBAAECggEAOmcEKdZUgOUbnaXjOhQ2HOEB/XMdMuSpV+zAEBegDJtTcGpKFokthOQzdJtyHh2FPlypAjUr9czWoAjZFAyeCJMZksCHnIVozTwmPsEyrSBZfUftKRp3ecnIn57nGMI32hS7Fqeh4UpUpshrlfWsGHRUGIELEMAsFCg+aTXNtUS//xaa6VIN0bVlW0nnPBFTH/4LZPjz154HLc6ogEMF/I0+lFFyr3y8U0KaLvgeziUIZWc1G+E6IEOXhyVfiGFOwNZeO90hqrMMDONAAmfuPeMhYogKrTZeBKr6Cj4wrbiaizGfvT65nlAfWZQhzroCAuDJv+8yZqXhRUb/dNgaYQKBgQDIOEWHXs6dgoa/qLjbonRBMux1/OimPklTDd2/8bBt9rsh733Io02wNNQDkdXbsyfh4TKBdaXkWCVJtTDhecb4wx5KqQSufTL/fc5VCbXV2MTUWYfa5ANoyYUxywLRGHLTUr7h83IJHVTNLnNK2pcgzarJQelR5plqXFrFpW3QiwKBgQC+oTK/7B4Z5veYyDevt6CsnWd16Ezf9R3xl1q8/0lHKIckqTMI6Y3e9eHl3Ks9+5qRX9IFsIiuDETQc/Vf2iQhoDnNEDKsq4M+zzgSwLyX83iULm2TyAhWxHpHxP/pV/hZJRvkiQqrWZyJGo/iebMowMcsoD0/UKjONtf2uw5BfwKBgCvEUT6UNOotfDMtOKuaSXtvHE12aYzdlL25DD5HIOAIzUNPzNktCXB5O9dD69HIyfEQfy/Zfdf6hLwmBXdkXWBHlrmPVNyntZwv7gicxMjoR657m7z3lt8zHhCSZh07EO6s6dkOlGD1rnHjs4QrYFnlW23/IHesxuB/K8CEtwqdAoGBALIdxmCDS6DiKUjNExMRXYTL0ibeNi4wRpk81aCwW5SsbpG3sqLKLzA7yKXRKQPSOptIz+FXEG6nsgifwI6eMZJltnw/fUnPO+ONFRSYl97WtDyooOQrTHdeP+8dSAyMkNUikkxYK+rs4RFOC1EOa3NRpQiydGfbgs6jeejk4K4bAoGBALTG9fS2xet6qi+uVgX3zmMzOcK9GeParNxlrnb0ZAhIizGFNUgjtIvUkZRc7vvpUxGc3/CZnFQEubbg2lLFg5PbHGXDLA9TcvfI4KUO+bzvM1F4hv208RL2YdnjvG4UF1RRYsX+fQzrrLHpugKiBX11nrgLt2VCpxH/LhKU78ZX";

    public CallbackBean getCallback() {
        return callback;
    }

    public void setCallback(CallbackBean callback) {
        this.callback = callback;
    }

    public static class CallbackBean {
        /**
         * app_id : 2088921884613118
         * biz_content : {"body":"余额充值","subject":"充值","out_trade_no":"2018041010260223650","total_amount":"10","product_code":"QUICK_MSECURITY_PAY","goods_type":"1"}
         * charset : UTF-8
         * format : JSON
         * method : alipay.trade.app.pay
         * notify_url : http://park.deyuelou.top/api/v1.notify/alipay
         * sign_type : RSA2
         * timestamp : 2018-04-10 10:26:02
         * version : 1.0
         * sign : 1jHuqDsAjBBcV/+NSBK/d5PmwJsI1JqTaiV5x/2F6wPBCTCQIdpEohpH/z+py8AABhXl8Hu+FnOkEkhCM74VUTVHjDSHqTEpp4IsGHQhAwqUVqr2b8qzWr86CGwRgXCSnlG5qtYa//8k6731SRjYiRdzE0MqjDUgDCDG4c3wUn0vjHW+KVko/0+qC3VmUjr8rZ214wTG3MzWEmBGQqSEzMeaRODYj26U/4sOAvau5FdJhKjia1E+1fL42/4u4Um98+xsqplOS/vLiz9OVaGU1bifaHM7cf6KoJLzxFUmguFt/uj2aijGkwTQoEULO2Bf5x6b8Szq82ZJAsT9xRL7MQ==
         */

        private String app_id;
        private BizContentBean biz_content;
        private String charset;
        private String format;
        private String method;
        private String notify_url;
        private String sign_type;
        private String timestamp;
        private String version;
        private String sign;

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public BizContentBean getBiz_content() {
            return biz_content;
        }

        public void setBiz_content(BizContentBean biz_content) {
            this.biz_content = biz_content;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }

        public String getSign_type() {
            return sign_type;
        }

        public void setSign_type(String sign_type) {
            this.sign_type = sign_type;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public static class BizContentBean {
            /**
             * body : 余额充值
             * subject : 充值
             * out_trade_no : 2018041010260223650
             * total_amount : 10
             * product_code : QUICK_MSECURITY_PAY
             * goods_type : 1
             */

            private String body;
            private String subject;
            private String out_trade_no;
            private String total_amount;
            private String product_code;
            private String goods_type;

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getOut_trade_no() {
                return out_trade_no;
            }

            public void setOut_trade_no(String out_trade_no) {
                this.out_trade_no = out_trade_no;
            }

            public String getTotal_amount() {
                return total_amount;
            }

            public void setTotal_amount(String total_amount) {
                this.total_amount = total_amount;
            }

            public String getProduct_code() {
                return product_code;
            }

            public void setProduct_code(String product_code) {
                this.product_code = product_code;
            }

            public String getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(String goods_type) {
                this.goods_type = goods_type;
            }

            public String toString() {
                return new Gson().toJson(this);
            }
        }

        public String getOrderInfo() {
//            Map<String, String> map = new HashMap<>();
//            map.put("app_id", app_id);
//            map.put("biz_content", biz_content.toString());
//            map.put("charset", charset);
//            map.put("method", method);
//            map.put("sign_type", sign_type);
//            map.put("timestamp", timestamp);
//            map.put("timestamp", timestamp);
//            map.put("version", version);
//            String orderParam = buildOrderParam(map);
//            return orderParam + "&" + getSign(map, RSA2_PRIVATE, true);
            StringBuilder builder = new StringBuilder("app_id=");
            builder.append(app_id).append("&").append("biz_content=").append(URLEncoder.encode(biz_content.toString()))
                    .append("&").append("charset=").append(URLEncoder.encode(charset))
                    .append("&").append("method=").append(URLEncoder.encode(method))
                    .append("&").append("sign_type=").append(URLEncoder.encode(sign_type))
                    .append("&").append("timestamp=").append(URLEncoder.encode(timestamp))
                    .append("&").append("version=").append(URLEncoder.encode(version))
                    .append("&").append("sign=").append(URLEncoder.encode(sign));
            return builder.toString();
        }

        public String buildOrderParam(Map<String, String> map) {
            List<String> keys = new ArrayList<String>(map.keySet());

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < keys.size() - 1; i++) {
                String key = keys.get(i);
                String value = map.get(key);
                sb.append(buildKeyValue(key, value, true));
                sb.append("&");
            }

            String tailKey = keys.get(keys.size() - 1);
            String tailValue = map.get(tailKey);
            sb.append(buildKeyValue(tailKey, tailValue, true));

            return sb.toString();
        }

        /**
         * 拼接键值对
         *
         * @param key
         * @param value
         * @param isEncode
         * @return
         */
        private String buildKeyValue(String key, String value, boolean isEncode) {
            StringBuilder sb = new StringBuilder();
            sb.append(key);
            sb.append("=");
            if (isEncode) {
                try {
                    sb.append(URLEncoder.encode(value, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    sb.append(value);
                }
            } else {
                sb.append(value);
            }
            return sb.toString();
        }

        /**
         * 对支付参数信息进行签名
         *
         * @param map 待签名授权信息
         * @return
         */
        public String getSign(Map<String, String> map, String rsaKey, boolean rsa2) {
            List<String> keys = new ArrayList<>(map.keySet());
            // key排序
            Collections.sort(keys);

            StringBuilder authInfo = new StringBuilder();
            for (int i = 0; i < keys.size() - 1; i++) {
                String key = keys.get(i);
                String value = map.get(key);
                authInfo.append(buildKeyValue(key, value, false));
                authInfo.append("&");
            }

            String tailKey = keys.get(keys.size() - 1);
            String tailValue = map.get(tailKey);
            authInfo.append(buildKeyValue(tailKey, tailValue, false));

            String oriSign = SignUtils.sign(authInfo.toString(), rsaKey, rsa2);
            String encodedSign = "";

            try {
                encodedSign = URLEncoder.encode(oriSign, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "sign=" + encodedSign;
        }

    }
}
