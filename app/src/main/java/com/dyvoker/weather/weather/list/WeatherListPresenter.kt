package com.dyvoker.weather.weather.list

import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * I decided to use this approach - Presenter + Presenter.View.
 * There is a problem with naming - name "View" is already using by Android.
 * It's a kind of compromise.
 * On my previous job, we used the name "Widget" for View from MVP, but it also uses by Android :)
 */
class WeatherListPresenter(
    private val repository: WeatherRepository
) : WeatherListContract.Presenter {

    private lateinit var view: WeatherListContract.View

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun attach(view: WeatherListContract.View) {
        this.view = view
    }

    override fun updateForecast(coordinates: MapPoint) {
        GlobalScope.launch(Dispatchers.Main) {
            val list = repository.getForecastWeather(coordinates)
            view.showForecast(list)
        }
    }
}