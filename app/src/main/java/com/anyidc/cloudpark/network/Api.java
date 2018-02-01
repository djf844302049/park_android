package com.anyidc.cloudpark.network;


public class Api {
    public static ApiService movieService;

    public static final String BASE_URL = "http://lt1.uhabc.com/";


    public static ApiService getDefaultService() {
        if (movieService == null) {
            movieService = RxRetrofit.getRetrofit(BASE_URL).create(ApiService.class);
        }
        return movieService;
    }
}