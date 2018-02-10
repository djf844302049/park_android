package com.anyidc.cloudpark.network;

import com.anyidc.cloudpark.utils.AesUtil;
import com.anyidc.cloudpark.utils.SpUtils;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/1.
 */

public class HeaderIntercept implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder newBuilder = chain.request().newBuilder();
        newBuilder.addHeader("sign", URLEncoder.encode(AesUtil.getSign().trim()));
        newBuilder.addHeader("version", URLEncoder.encode(String.valueOf(SpUtils.get(SpUtils.VERSION_CODE, 0))));
        newBuilder.addHeader("type", URLEncoder.encode("android"));
        newBuilder.addHeader("did", URLEncoder.encode((String) SpUtils.get(SpUtils.DID, "")));
        String token = (String) SpUtils.get(SpUtils.TOKEN, "");
//            if (!TextUtils.isEmpty(token)) {
        newBuilder.addHeader("token", URLEncoder.encode(token));
//        }
        return chain.proceed(newBuilder.build());
    }
}
