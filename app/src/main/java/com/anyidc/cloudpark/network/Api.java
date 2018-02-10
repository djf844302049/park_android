package com.anyidc.cloudpark.network;


public class Api {
    public static ApiService movieService;

    public static final String DEBUG_URL = "http://park.deyuelou.top";
    public static final String BASE_URL = "http://park.deyuelou.com";


    public static ApiService getDefaultService() {
        if (movieService == null) {
            movieService = RxRetrofit.getRetrofit(DEBUG_URL).create(ApiService.class);
        }
        return movieService;
    }
}