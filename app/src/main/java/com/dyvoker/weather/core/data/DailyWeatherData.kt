package com.dyvoker.weather.core.data

import com.google.gson.annotations.SerializedName

data class DailyWeatherData(
    @SerializedName("time")
    val timestamp: Long,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("temperatureHigh")
    val temperatureHigh: Float,
    @SerializedName("temperatureLow")
    val temperatureLow: Float
)