package com.anyidc.cloudpark.network;

import com.anyidc.cloudpark.moduel.AddCarBean;
import com.anyidc.cloudpark.moduel.AlPayBean;
import com.anyidc.cloudpark.moduel.BankCardBean;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.CenterBean;
import com.anyidc.cloudpark.moduel.CityAreaBean;
import com.anyidc.cloudpark.moduel.DrawCashBean;
import com.anyidc.cloudpark.moduel.HotAreaBean;
import com.anyidc.cloudpark.moduel.IndexBean;
import com.anyidc.cloudpark.moduel.InfoBean;
import com.anyidc.cloudpark.moduel.InitBean;
import com.anyidc.cloudpark.moduel.LoginRegisterBean;
import com.anyidc.cloudpark.moduel.MessageBean;
import com.anyidc.cloudpark.moduel.MonitorVideoBean;
import com.anyidc.cloudpark.moduel.MyAppointmentBean;
import com.anyidc.cloudpark.moduel.MyCarBean;
import com.anyidc.cloudpark.moduel.MyShareBean;
import com.anyidc.cloudpark.moduel.OptReasonBean;
import com.anyidc.cloudpark.moduel.ParkDetailBean;
import com.anyidc.cloudpark.moduel.ParkInfoBean;
import com.anyidc.cloudpark.moduel.ParkSearchBean;
import com.anyidc.cloudpark.moduel.ShareParkBean;
import com.anyidc.cloudpark.moduel.StopRecordBean;
import com.anyidc.cloudpark.moduel.TimeBean;
import com.anyidc.cloudpark.moduel.TransactionBean;
import com.anyidc.cloudpark.moduel.UnitStateBean;
import com.anyidc.cloudpark.moduel.UpdateImgBean;
import com.anyidc.cloudpark.moduel.UsualQuestionBean;
import com.anyidc.cloudpark.moduel.WalletInfoBean;
import com.anyidc.cloudpark.moduel.WxPayBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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
    Observable<BaseEntity<IndexBean>> getIndex(@Field("lat") double lat, @Field("lng") double lng);

    /**
     * 获取基本信息接口
     */
    @GET("api/v1/user/1")
    Observable<BaseEntity<InfoBean>> getUserInfo();

    /**
     * 基本信息提交接口
     */
    @POST("api/v1/baseinfo")
    @FormUrlEncoded
    Observable<BaseEntity<InfoBean>> updateInfo(@Field("header_img") String header_img
            , @Field("sex") Integer sex, @Field("username") String username);

    /**
     * 身份认证申请提交接口
     */
    @POST("api/v1/auth")
    @FormUrlEncoded
    Observable<BaseEntity<AddCarBean>> idConfirm(@Field("real_name") String realName
            , @Field("id_pos") String idPos, @Field("id_neg") String idNeg, @Field("id_no") String idNum);

    /**
     * 增加车辆接口
     */
    @POST("api/v1/addMyCar")
    @FormUrlEncoded
    Observable<BaseEntity<AddCarBean>> addCar(@Field("car_no") String carNum, @Field("new_energy") int newEnergy);

    /**
     * 车辆认证接口
     */
    @POST("api/v1/carAuth")
    @FormUrlEncoded
    Observable<BaseEntity> carAuth(@Field("id") String id
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
    Observable<BaseEntity<MonitorVideoBean>> watchCamera(@Field("parking_sn") String parkSn);

    /**
     * 热搜地区接口
     */
    @GET("api/v1/getHotSearch")
    Observable<BaseEntity<List<HotAreaBean>>> getHotSearch();

    /**
     * 停车场搜索请求接口
     */
    @POST("api/v1/parkingSearch")
    @FormUrlEncoded
    Observable<BaseEntity<ParkSearchBean>> parkingSearch(@Field("size") int size, @Field("page") int page
            , @Field("target") String target);

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
    Observable<BaseEntity<CenterBean>> center();

    /**
     * 用户意见反馈接口
     */
    @POST("api/v1/addAdvise")
    @FormUrlEncoded
    Observable<BaseEntity> addAdvise(@Field("content") String content, @Field("images") String images);

    /**
     * 常见问题接口
     */
    @GET("api/v1/getCommonQuestion")
    Observable<BaseEntity<List<UsualQuestionBean>>> getQuestion();

    /**
     * 获取用户车辆列表接口
     */
    @GET("api/v1/getUserCars")
    Observable<BaseEntity<List<MyCarBean>>> getUserCars();

    /**
     * 用户停车记录列表接口
     */
    @GET("api/v1/getOrderByUser")
    Observable<BaseEntity<StopRecordBean>> getParkRecord(@Query("page") int page, @Query("size") int size);

    /**
     * 获取用户支付设置接口
     */
    @GET("api/v1/paymentSetting")
    Observable<BaseEntity> getPaySetting();

    /**
     * 获取我的钱包接口
     */
    @GET("api/v1/myWallet")
    Observable<BaseEntity<WalletInfoBean>> getWalletInfo();

    /**
     * 获取交易明细接口
     */
    @GET("api/v1/getPaymentList")
    Observable<BaseEntity<TransactionBean>> getPayList(@Query("page") int page, @Query("size") int size);

    /**
     * 删除我的车辆接口
     */
    @POST("api/v1/delMyCar")
    @FormUrlEncoded
    Observable<BaseEntity> deleteCar(@Field("id") int id);

    /**
     * 我的消息接口
     */
    @GET("api/v1/getMyMessage")
    Observable<BaseEntity<MessageBean>> getMessages(@Query("page") int page, @Query("size") int size);

    /**
     * 删除我的消息接口
     */
    @POST("api/v1/delUserMessage")
    @FormUrlEncoded
    Observable<BaseEntity> deleteMessages(@Field("delete") int delete);

    /**
     * 用户添加银行卡接口
     */
    @POST("api/v1/addBankCard")
    @FormUrlEncoded
    Observable<BaseEntity> addBankCard(@Field("bank") String bank, @Field("card") String card
            , @Field("mobile") String mobile, @Field("realname") String realname, @Field("sfz") String sfz);

    /**
     * 获取用户银行卡接口
     */
    @GET("api/v1/getMyCard")
    Observable<BaseEntity<List<BankCardBean>>> getBankCard();

    /**
     * 删除银行卡请求接口
     */
    @GET("api/v1/deleteMyCard")
    Observable<BaseEntity> deleteBankCard();

    /**
     * 检测用户支付密码是否设置接口
     */
    @POST("api/v1/checkSurplusPassword")
    Observable<BaseEntity> isSetPayKey();

    /**
     * 用户设置支付密码接口
     */
    @POST("api/v1/setSurplusPassword")
    @FormUrlEncoded
    Observable<BaseEntity> setPayKey(@Field("surplusPassword") String surplusPassword);

    /**
     * 判断支付密码是否正确接口
     */
    @POST("api/v1/comfirmSurplusPassword")
    @FormUrlEncoded
    Observable<BaseEntity> checkPayKey(@Field("surplusPassword") String surplusPassword);

    /**
     * 修改支付密码请求接口
     */
    @POST("api/v1/resetSurplusPassword")
    @FormUrlEncoded
    Observable<BaseEntity> resetPayKey(@Field("surplusPassword") String surplusPassword);

    /**
     * 免密设置接口
     */
    @POST("api/v1/setFreePassword")
    @FormUrlEncoded
    Observable<BaseEntity> setFreePay(@Field("freePassword") int freePassword);

    /**
     * 管理员操作车位锁
     */
    @POST("api/v1/parkingControlByManager")
    @FormUrlEncoded
    Observable<BaseEntity> parkingControl(@Field("parking_sn") String parking_sn, @Field("control") String control
            , @Field("reason_code") String reason_code, @Field("reason_note") String reason_note, @Field("type") int type);

    /**
     * 获取共享车位列表接口
     */
    @POST("api/v1/getShareList")
    @FormUrlEncoded
    Observable<BaseEntity<ShareParkBean>> getShareParkList(@Field("parking_id") String parking_id);

    /**
     * 获取普通车位详情接口
     */
    @POST("api/v1/detail")
    @FormUrlEncoded
    Observable<BaseEntity> normalParkDetail(@Field("unit_id") String unit_id);

    /**
     * 共享车位详情接口
     */
    @POST("api/v1.parking_lot/ShareDetail")
    @FormUrlEncoded
    Observable<BaseEntity> shareParkDetail(@Field("unit_id") String unit_id);

    /**
     * 我的共享车位列表接口
     */
    @GET("api/v1.user_park/myshare")
    Observable<BaseEntity<MyShareBean>> getMyshare();

    /**
     * 我的预约记录列表接口
     *
     * @param status 0表示已经完成
     * @param page   要数
     * @param size   页面大小
     * @return
     */
    @POST("api/v1.user_park/appointment")
    @FormUrlEncoded
    Observable<BaseEntity<MyAppointmentBean>> getAppointment(@Field("status") String status, @Field("page") String page, @Field("size") String size);

    /**
     * 取消预约
     *
     * @param unitId 车位编号
     * @return
     */
    @POST("api/v1/cancelAppointment")
    @FormUrlEncoded
    Observable<BaseEntity> cancelAppointment(@Field("unit_id") String unitId);

    /**
     * 车辆到达
     *
     * @param unitId 车位编号
     * @return
     */
    @POST("api/v1/arrive")
    @FormUrlEncoded
    Observable<BaseEntity> arrive(@Field("unit_id") String unitId);

    /**
     * 车位预约（付款）
     * "subject"  标题
     * "detail"   描述
     * "money"  金额（元）
     * "product_id" 产品ID
     * "1"=>array("name" =>"充值",),
     * "2"=>array("name" =>"押金"),
     * "3"=>array("name" => "预约",),
     * "4"=>array("name" => "结算,出库",)
     * “pay_type” 支付类型ID "1"=>"alipay","2"=>"wxpay","3"=>"bankpay","4"=>"qianbao"
     * “unit_id”  （预约/结算）需要  车位ID
     */
    @POST("api/v1/dopay")
    @FormUrlEncoded
    Observable<BaseEntity> doPay(@Field("subject") String subject, @Field("detail") String detail
            , @Field("money") String money, @Field("product_id") int product_id
            , @Field("pay_type") int pay_type, @Field("unit_id") String unit_id, @Field("car_id") String car_id);


    /**
     * 支付宝支付接口
     */
    @POST("api/v1/dopay")
    @FormUrlEncoded
    Observable<BaseEntity<AlPayBean>> alPay(@Field("subject") String subject, @Field("detail") String detail
            , @Field("money") String money, @Field("product_id") int product_id
            , @Field("pay_type") int pay_type, @Field("unit_id") String unit_id, @Field("car_id") String car_id);

    /**
     * 微信支付接口
     */
    @POST("api/v1/dopay")
    @FormUrlEncoded
    Observable<BaseEntity<WxPayBean>> wxPay(@Field("subject") String subject, @Field("detail") String detail
            , @Field("money") String money, @Field("product_id") int product_id
            , @Field("pay_type") int pay_type, @Field("unit_id") String unit_id, @Field("car_id") String car_id);

    /**
     * 余额支付接口
     */
    @POST("api/v1/dopay")
    @FormUrlEncoded
    Observable<BaseEntity> balancePay(@Field("subject") String subject, @Field("detail") String detail
            , @Field("money") String money, @Field("product_id") int product_id
            , @Field("pay_type") int pay_type, @Field("unit_id") String unit_id, @Field("car_id") String car_id);

    /**
     * 取消预约接口
     */
    @POST("api/v1.order/cancelAppointment")
    Observable<BaseEntity> cancelOrder(@Field("unit_id") String unit_id);

    /**
     * 车辆到达接口
     */
    @POST("park.deyuelou.com")
    Observable<BaseEntity> carArrive(@Field("unit_id") String unit_id);

    /**
     * 身份验证接口
     */
    @POST("api/v1.user/checkMobile")
    @FormUrlEncoded
    Observable<BaseEntity> idConfirm(@Field("mobile") String mobile, @Field("code") String code);

    /**
     * 停车场区域搜索请求接口
     */
    @POST("api/v1.parking/parkingSearchById")
    @FormUrlEncoded
    Observable<BaseEntity<ParkSearchBean>> searchParkById(@Field("page") int page, @Field("size") int size, @Field("area_id") int area_id);

    /**
     * 根据经纬度返回附近停车场接口
     */
    @POST("api/v1.parking/parkingSearchByRange")
    @FormUrlEncoded
    Observable<BaseEntity<ParkSearchBean>> searchParkNearby(@Field("page") int page, @Field("size") int size
            , @Field("lat") double lat, @Field("lng") double lng);

    /**
     * 根据城市返回对应的区信息接口
     */
    @POST("api/v1.region/getAreaByName")
    @FormUrlEncoded
    Observable<BaseEntity<List<CityAreaBean>>> getAreaInfo(@Field("city") String city);

    /**
     * 获取车场详情
     */
    @POST("api/v1/getList")
    @FormUrlEncoded
    Observable<BaseEntity<ParkDetailBean>> getParkDetail(@Field("parking_id") String parking_id);

    /**
     * 查询车位订单详情
     */
    @POST("api/v1.Parking_lot/getUnitOrder")
    @FormUrlEncoded
    Observable<BaseEntity<ParkInfoBean>> getParkOrderInfo(@Field("unit_id") String unit_id);

    /**
     * 提现
     */
    @POST("api/v1.order/tixian")
    @FormUrlEncoded
    Observable<BaseEntity<DrawCashBean>> drawCash(@Field("money") String money, @Field("bank_id") String bank_id);

    /**
     * 退押金 0-银行卡；1-支付宝；2-微信；3-银联
     */
    @POST("api/v1/depRefund")
    @FormUrlEncoded
    Observable<BaseEntity> drawDeposit(@Field("bank_id") String bank_id, @Field("pay_id") int pay_id
            , @Field("pay_account") String pay_account);

    /**
     * 操作车位锁原因
     */
    @POST("/api/v1/parkingControlReason")
    @FormUrlEncoded
    Observable<BaseEntity<List<OptReasonBean>>> optReason(@Field("type") int type);

    /**
     * 操作车位锁原因
     */
    @POST("/api/v1/unitDevice")
    @FormUrlEncoded
    Observable<BaseEntity<UnitStateBean>> unitState(@Field("unit_id") String unit_id);
}
