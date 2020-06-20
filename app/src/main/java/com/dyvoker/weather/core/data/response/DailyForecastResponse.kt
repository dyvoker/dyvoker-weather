package com.dyvoker.weather.core.data.response

import com.dyvoker.weather.core.data.DailyForecastData
import com.google.gson.annotations.SerializedName

data class DailyForecastResponse(
    @SerializedName("daily")
    val dailyForecastData: DailyForecastData
)