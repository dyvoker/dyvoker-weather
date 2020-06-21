package com.dyvoker.weather.core.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class DailyWeatherData(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    var latitude: Double,
    var longitude: Double,
    var creationTimestamp: Long,
    @SerializedName("time")
    val timestamp: Long,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("temperatureHigh")
    val temperatureHigh: Float,
    @SerializedName("temperatureLow")
    val temperatureLow: Float
)