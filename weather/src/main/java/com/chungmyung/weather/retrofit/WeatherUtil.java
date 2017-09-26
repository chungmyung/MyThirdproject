package com.chungmyung.weather.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2017-09-26.
 */

public class WeatherUtil {
    private final WeatherAPI mApiService ;

    public WeatherUtil() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // 만들어서
                .build();
        mApiService = retrofit.create(WeatherAPI.class);

    }

    public WeatherAPI getApiService() {
        return mApiService;
    }
}
