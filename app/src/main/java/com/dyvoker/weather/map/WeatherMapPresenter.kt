package com.dyvoker.weather.map

import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WeatherMapPresenter(
    private val repository: WeatherRepository
) : WeatherMapContract.Presenter {

    private lateinit var view: WeatherMapContract.View

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun attach(view: WeatherMapContract.View) {
        this.view = view
    }

    override fun updateWeather(coordinates: MapPoint) {
        GlobalScope.launch(Dispatchers.Main) {
            val currentWeather = repository.getCurrentWeather(coordinates)
            view.showWeather(currentWeather)
        }
    }
}