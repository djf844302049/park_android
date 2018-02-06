package com.anyidc.cloudpark.utils;

import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2018/2/1.
 */

public class AesUtil {
    private static final String KEY = "haicang123567890";

    // 加密
    public static String encrypt(String sSrc) throws Exception {
        byte[] raw = KEY.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    // 解密
    public static String decrypt(String sSrc) throws Exception {
        try {
            byte[] raw = KEY.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64.decode(sSrc, Base64.DEFAULT);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static String getSign() throws Exception {
        StringBuilder builder = new StringBuilder();
        String string = builder.append("did=")
                .append(SpUtils.get(SpUtils.DID, ""))
                .append("&time=")
                .append(SpUtils.get(SpUtils.TIME, ""))
                .toString();
        Log.e("String", string);
        String encrypt = encrypt(string);
        Log.e("encrypt", encrypt);
        return encrypt;
    }
}
