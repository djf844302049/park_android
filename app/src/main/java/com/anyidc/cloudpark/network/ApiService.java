package com.anyidc.cloudpark.network;

import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.TimeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by necer on 2017/6/28.
 */

public interface ApiService {

    @GET("api/time")
    Observable<BaseEntity<TimeBean>> getTime();

    @GET("api/v1/init")
    Observable<BaseEntity<String>> appInit();

}
