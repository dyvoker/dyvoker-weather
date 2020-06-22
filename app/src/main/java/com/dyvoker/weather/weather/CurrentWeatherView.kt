package com.dyvoker.weather.weather

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.dyvoker.weather.common.CurrentWeatherDrawable
import com.dyvoker.weather.core.data.CurrentWeatherData

/**
 * If we will need optimization, there is always a way to copy all code from CurrentWeatherDrawable
 * and optimize its usage in View, such as changing sizes only in onSizeChanged method.
 */
class CurrentWeatherView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
) : View(context, attrs, defStyleAttr) {

    private var currentWeatherDrawable: CurrentWeatherDrawable? = null

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null)
            : this(context, attrs, 0)

    override fun onDraw(canvas: Canvas) {
        currentWeatherDrawable?.run {
            setBounds(0, 0, width, height)
            draw(canvas)
        }
    }

    fun setCurrentWeatherData(newData: CurrentWeatherData) {
        if (currentWeatherDrawable == null) {
            currentWeatherDrawable = CurrentWeatherDrawable(context, newData, isMarker = false)
        } else {
            currentWeatherDrawable?.setData(newData)
        }
        invalidate()
    }
}