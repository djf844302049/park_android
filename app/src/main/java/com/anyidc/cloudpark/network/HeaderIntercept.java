package com.anyidc.cloudpark.network;

import android.util.Log;

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
        Log.e("tag", "hahahaha");
        Request newRequest = chain.request().newBuilder()
                .addHeader("sign", "111")
                .addHeader("version", "2")
                .addHeader("type", "Android")
                .addHeader("did", "to1111111ken")
                .addHeader("token", "111111111111")
                .build();
        return chain.proceed(newRequest);
    }
}
