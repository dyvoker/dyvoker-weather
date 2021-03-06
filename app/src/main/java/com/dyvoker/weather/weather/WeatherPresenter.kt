package com.dyvoker.weather.weather

import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.data.Resource
import com.dyvoker.weather.core.repository.CityRepository
import com.dyvoker.weather.core.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WeatherPresenter(
    private val repository: WeatherRepository,
    private val cityRepository: CityRepository
) : WeatherContract.Presenter {

    private lateinit var view: WeatherContract.View

    override fun attach(view: WeatherContract.View) {
        this.view = view
        GlobalScope.launch(Dispatchers.Main) {
            view.showCitiesTabs(cityRepository.getCities())
        }
    }

    override fun updateWeather(coordinates: MapPoint) {
        GlobalScope.launch(Dispatchers.Main) {
            val currentWeather = repository.getCurrentWeather(coordinates)
            when (currentWeather.status) {
                Resource.Status.SUCCESS -> view.showWeather(currentWeather.data!!)
                Resource.Status.ERROR -> view.showLoadingError()
                Resource.Status.LOADING -> {} // Someday I will write all the code...
            }
        }
    }

    override fun weatherMapViewClosed() {
        GlobalScope.launch(Dispatchers.Main) {
            view.showCitiesTabs(cityRepository.getCities())
        }
    }

    override fun clickMapButton() {
        view.openWeatherMap()
    }

    override fun removeCityClick(cityName: String) {
        GlobalScope.launch(Dispatchers.Main) {
            cityRepository.removeCity(cityName)
            view.showCitiesTabs(cityRepository.getCities())
        }
    }
}