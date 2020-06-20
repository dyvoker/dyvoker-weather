package com.dyvoker.weather.core

import com.dyvoker.weather.core.data.WeatherItemData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DarkSkyApiService {

    @GET("forecast/{key}/{latitude},{longitude}")
    fun getWeather(
        @Path("key") key: String,
        @Path("latitude") latitude: String,
        @Path("longitude") longitude: String
    ): Call<List<WeatherItemData>>
}