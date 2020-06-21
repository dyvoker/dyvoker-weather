package com.dyvoker.weather.core

import com.dyvoker.weather.core.data.response.CurrentWeatherResponse
import com.dyvoker.weather.core.data.response.DailyForecastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DarkSkyApiService {

    @GET("forecast/API_KEY/{latitude},{longitude}")
    fun getForecastWeather(
        @Path("latitude") latitude: String,
        @Path("longitude") longitude: String,
        @Query("lang") locale: String = "en"
    ): Call<DailyForecastResponse>

    @GET("forecast/API_KEY/{latitude},{longitude}")
    fun getCurrentWeather(
        @Path("latitude") latitude: String,
        @Path("longitude") longitude: String,
        @Query("lang") locale: String = "en"
    ): Call<CurrentWeatherResponse>
}