package com.dyvoker.weather.list

import androidx.annotation.DrawableRes
import com.dyvoker.weather.R

enum class WeatherIcon {
    CLEAR_DAY,
    CLEAR_NIGHT,
    RAIN,
    SNOW,
    SLEET,
    WIND,
    FOG,
    CLOUDY,
    PARTLY_CLOUDY_DAY,
    PARTLY_CLOUDY_NIGHT,
    HAIL,
    THUNDERSTORM,
    TORNADO;

    @DrawableRes
    fun getResId(): Int =
        when (this) {
            CLEAR_DAY -> R.drawable.ic_sunny
            CLEAR_NIGHT -> R.drawable.ic_clear_night
            RAIN -> R.drawable.ic_rain
            SNOW -> R.drawable.ic_snow
            SLEET -> R.drawable.ic_sleet
            WIND -> R.drawable.ic_wind
            FOG -> R.drawable.ic_fog
            CLOUDY -> R.drawable.ic_cloudy
            PARTLY_CLOUDY_DAY -> R.drawable.ic_cloudy
            PARTLY_CLOUDY_NIGHT -> R.drawable.ic_partly_cloudy_night
            HAIL -> R.drawable.ic_hail
            THUNDERSTORM -> R.drawable.ic_thunderstorm
            TORNADO -> R.drawable.ic_tornado
        }
}