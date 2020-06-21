package com.dyvoker.weather.core

import androidx.annotation.DrawableRes
import com.dyvoker.weather.R

class DarkSkyUtils {
    companion object {
        @DrawableRes
        fun getIconId(icon: String): Int = when (icon) {
            "clear-day" -> R.drawable.ic_sunny
            "clear-night" -> R.drawable.ic_clear_night
            "rain" -> R.drawable.ic_rain
            "snow" -> R.drawable.ic_snow
            "sleet" -> R.drawable.ic_sleet
            "wind" -> R.drawable.ic_wind
            "fog" -> R.drawable.ic_fog
            "cloudy" -> R.drawable.ic_cloudy
            "partly-cloudy-day" -> R.drawable.ic_cloudy
            "partly-cloudy-night" -> R.drawable.ic_partly_cloudy_night
            "hail" -> R.drawable.ic_hail
            "thunderstorm" -> R.drawable.ic_thunderstorm
            "tornado" -> R.drawable.ic_tornado
            else -> R.drawable.ic_sunny
        }
    }
}