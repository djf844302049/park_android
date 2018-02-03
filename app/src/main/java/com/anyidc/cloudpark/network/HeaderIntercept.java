package com.anyidc.cloudpark.network;

import android.text.TextUtils;

import com.anyidc.cloudpark.utils.SpUtils;

import java.io.IOException;

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
        newBuilder.addHeader("sign", "111");
        newBuilder.addHeader("version", String.valueOf(SpUtils.get(SpUtils.VERSIONCODE, 0)));
        newBuilder.addHeader("type", "Android");
        newBuilder.addHeader("did", (String) SpUtils.get(SpUtils.DID, ""));
        String token = (String) SpUtils.get(SpUtils.TOKEN, "");
        if (!TextUtils.isEmpty(token)) {
            newBuilder.addHeader("token", token);
        }
        return chain.proceed(newBuilder.build());
    }
}
