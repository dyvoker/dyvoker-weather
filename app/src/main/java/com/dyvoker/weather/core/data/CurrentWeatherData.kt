package com.dyvoker.weather.core.data

import com.google.gson.annotations.SerializedName

data class CurrentWeatherData(
    @SerializedName("time")
    val timestamp: Long,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("temperature")
    val temperature: Float
)