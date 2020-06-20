package com.dyvoker.weather.weather

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
class CurrentWeatherPresenter(
    private val repository: WeatherRepository
) : CurrentWeatherContract.Presenter {

    private lateinit var view: CurrentWeatherContract.View

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun attach(view: CurrentWeatherContract.View) {
        this.view = view
    }

    override fun updateWeather(coordinates: MapPoint) {
        GlobalScope.launch(Dispatchers.Main) {
            val currentWeather = repository.getCurrentWeather(coordinates)
            view.showWeather(currentWeather)
        }
    }
}