package com.dyvoker.weather.list

import com.dyvoker.weather.common.data.MapPoint
import com.dyvoker.weather.common.data.WeatherItemData

/**
 * I decided to use this approach - Presenter + Presenter.View.
 * There is a problem with naming - name "View" is already using by Android.
 * It's a kind of compromise.
 * On my previous job, we used the name "Widget" for View from MVP, but it also uses by Android :)
 */
class CurrentWeatherPresenter(
    private val view: View
) {

    // val repo by injecting.

    fun updateWeather(coordinates: MapPoint) {
        // Load from repo and show to view.
    }

    interface View {
        fun showWeather(data: WeatherItemData)
    }
}