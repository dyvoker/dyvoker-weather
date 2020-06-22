package com.dyvoker.weather.weather.list

import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WeatherListPresenter(
    private val repository: WeatherRepository
) : WeatherListContract.Presenter {

    private lateinit var view: WeatherListContract.View
    private lateinit var coordinates: MapPoint

    override fun initCoordinates(point: MapPoint) {
        coordinates = point
    }

    override fun attach(view: WeatherListContract.View) {
        this.view = view
    }

    override fun updateForecast() {
        GlobalScope.launch(Dispatchers.Main) {
            val list = repository.getForecastWeather(coordinates)
            view.showForecast(list)
        }
    }
}