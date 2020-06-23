package com.dyvoker.weather.weather

import com.dyvoker.weather.common.BaseContract
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint

class WeatherContract {

    interface Presenter : BaseContract.Presenter<View> {
        fun updateWeather(coordinates: MapPoint)
        fun weatherMapViewClosed()
        fun clickMapButton()
        fun removeCityClick(cityName: String)
    }

    interface View : BaseContract.View {
        fun showWeather(data: CurrentWeatherData)
        fun showCitiesTabs(cities: Map<String, MapPoint>)
        fun openWeatherMap()
        fun showLoadingError()
    }
}
