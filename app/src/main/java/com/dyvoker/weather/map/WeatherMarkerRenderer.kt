package com.dyvoker.weather.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import com.dyvoker.weather.common.CurrentWeatherDrawable
import com.dyvoker.weather.core.data.CurrentWeatherData

/**
 * Renders given weather info to bitmap.
 */
class WeatherMarkerRenderer(
    context: Context,
    data: CurrentWeatherData
) {
    private val currentWeatherDrawable = CurrentWeatherDrawable(context, data)

    fun setCurrentWeatherData(newData: CurrentWeatherData) {
        currentWeatherDrawable.setData(newData)
    }

    fun renderBitmap(width: Int) : Bitmap {
        val height = width / 3 * 2 // Aspect ratio.
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        currentWeatherDrawable.setBounds(0, 0, width, height)
        currentWeatherDrawable.draw(canvas)
        return bitmap
    }
}