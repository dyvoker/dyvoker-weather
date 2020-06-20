package com.dyvoker.weather.core.data

import com.google.gson.annotations.SerializedName

data class DailyForecastData(
    @SerializedName("data")
    val list: List<DailyWeatherData>
)