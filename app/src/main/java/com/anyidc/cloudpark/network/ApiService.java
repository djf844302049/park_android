package com.anyidc.cloudpark.network;

import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.InfoBean;
import com.anyidc.cloudpark.moduel.InitBean;
import com.anyidc.cloudpark.moduel.LoginRegisterBean;
import com.anyidc.cloudpark.moduel.TimeBean;
import com.anyidc.cloudpark.moduel.UpdateImgBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    Observable<BaseEntity<InitBean>> appInit();

    /**
     * 获取验证码
     */
    @POST("api/v1/identify")
    @FormUrlEncoded
    Observable<BaseEntity> getCode(@Field("mobile") String mobile);

    /**
     * 密码登录接口
     */
    @POST("api/v1/login")
    @FormUrlEncoded
    Observable<BaseEntity<LoginRegisterBean>> loginByPassword(@Field("mobile") String mobile
            , @Field("password") String password);

    /**
     * 验证码登录接口
     */
    @POST("api/v1/login")
    @FormUrlEncoded
    Observable<BaseEntity<LoginRegisterBean>> loginByCode(@Field("mobile") String mobile
            , @Field("code") String password);

    /**
     * 注册接口
     */
    @POST("api/v1/register")
    @FormUrlEncoded
    Observable<BaseEntity<LoginRegisterBean>> register(@Field("mobile") String mobile
            , @Field("password") String password, @Field("code") String code);

    /**
     * 图片上传接口
     */
    @POST("api/v1/image")
    @Multipart
    Observable<BaseEntity<UpdateImgBean>> uploadImg(@Part MultipartBody.Part part);

    /**
     * 忘记密码接口
     */
    @POST("api/v1/forget")
    @FormUrlEncoded
    Observable<BaseEntity<LoginRegisterBean>> forgetPassword(@Field("mobile") String mobile
            , @Field("password") String password, @Field("code") String code);

    /**
     * 首页接口
     */
    @POST("api/v1/index")
    @FormUrlEncoded
    Observable<BaseEntity<String>> getIndex(@Field("lat") double lat, @Field("lng") double lng);

    /**
     * 获取基本信息接口
     */
    @GET("api/v1/user/1")
    Observable<BaseEntity<String>> getUserInfo();

    /**
     * 基本信息提交接口
     */
    @POST("api/v1/baseinfo")
    @FormUrlEncoded
    Observable<BaseEntity<InfoBean>> updateInfo(@Field("header_img") String header_img
            , @Field("sex") int sex, @Field("username") String username);

    /**
     * 身份认证申请提交接口
     */
    @POST("api/v1/auth")
    @FormUrlEncoded
    Observable<BaseEntity<String>> idConfirm(@Field("real_name") String realName
            , @Field("id_pos") String idPos, @Field("id_neg") String idNeg,@Field("id_num") String idNum);

    /**
     * 增加车辆接口
     */
    @POST("api/v1/addMyCar")
    @FormUrlEncoded
    Observable<BaseEntity<String>> addCar(@Field("car_no") String carNum, @Field("new_energy") int newEnergy);

    /**
     * 车辆认证接口
     */
    @POST("api/v1/carAuth")
    @FormUrlEncoded
    Observable<BaseEntity<String>> carAuth(@Field("id") String id
            , @Field("license_pos") String licensePos, @Field("license_neg") String licenseNeg);

    /**
     * 管理员控制车位升降接口
     */
    @POST("api/v1/parkingControlByManager")
    @FormUrlEncoded
    Observable<BaseEntity<String>> parkControl(@Field("parking_sn") String parkSn, @Field("control") String control);

    /**
     * 停车位摄像头播放地址请求接口
     */
    @POST("api/v1/watchCamera")
    @FormUrlEncoded
    Observable<BaseEntity<String>> watchCamera(@Field("parking_sn") String parkSn);

    /**
     * 热搜地区接口
     */
    @GET("api/v1/getHotSearch")
    Observable<BaseEntity<String>> getHotSearch();

    /**
     * 停车场搜索请求接口
     */
    @POST("api/v1/parkingSearch")
    @FormUrlEncoded
    Observable<BaseEntity<String>> parkingSearch(@Field("size") String size, @Field("page") int page);

    /**
     * 首页预约请求接口
     */
    @POST("api/v1/parkingAppointment")
    @FormUrlEncoded
    Observable<BaseEntity<String>> parkOrder();

    /**
     * 个人中心接口
     */
    @GET("api/v1/center")
    Observable<BaseEntity<String>> center();

    /**
     * 用户意见反馈接口
     */
    @POST("api/v1/addAdvise")
    @FormUrlEncoded
    Observable<BaseEntity<String>> addAdvise(@Field("content") String content, @Field("images") String images);

    /**
     * 常见问题接口
     */
    @GET("api/v1/getCommonQuestion")
    Observable<BaseEntity<String>> getQuestion();

    /**
     * 获取用户车辆列表接口
     */
    @GET("api/v1/getUserCars")
    Observable<BaseEntity<String>> getUserCars();
}
