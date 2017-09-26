package com.chungmyung.weather.retrofit;

import com.chungmyung.weather.models.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by user on 2017-09-25.
 */

public interface WeatherAPI {

    String BASE_URL = "https://api.openweathermap.org/data/2.5/" ;
    String APP_ID ="3a240c844a1e89e34e8da05942d5d219";


    @GET("weather?lang=kr&units=metric&appid="+APP_ID)
    Call<CurrentWeather> getCurrentWeather(@Query("q") String cityName);

}
