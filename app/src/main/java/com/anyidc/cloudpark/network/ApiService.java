package com.anyidc.cloudpark.network;

import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.TimeBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by necer on 2017/6/28.
 */

public interface ApiService {

    /**
     * 获取时间戳
     */
    @GET("api/time")
    Observable<BaseEntity<TimeBean>> getTime();

    /**
     * 获取初始化数据
     */
    @GET("api/v1/init")
    Observable<BaseEntity<List<String>>> appInit();

    /**
     * 获取验证码
     */
    @POST("App/v1/identify")
    Observable<BaseEntity<String>> getCode(@Field("mobile") String mobile);

    /**
     * 登录接口
     */
    @POST("api/v1/login")
    Observable<BaseEntity<String>> login(@Field("mobile") String mobile
            , @Field("password") String password);

    /**
     * 登录接口
     */
    @POST("api/v1/register")
    Observable<BaseEntity<String>> register(@Field("mobile") String mobile
            , @Field("password") String password, @Field("code") String code);

    /**
     * 忘记密码接口
     */
    @POST("api/v1/forget")
    Observable<BaseEntity<String>> forgetPassword(@Field("mobile") String mobile
            , @Field("password") String password, @Field("code") String code);

    /**
     * 首页接口
     */
    @GET("api/v1/index")
    Observable<BaseEntity<String>> getIndex();

    /**
     * 图片上传接口
     */
    @POST("api/v1/image")
    Observable<BaseEntity<String>> uploadImg();
}
