package com.dyvoker.weather.core.data.response

import com.dyvoker.weather.core.data.CurrentWeatherData
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("currently")
    val currentWeather: CurrentWeatherData
)