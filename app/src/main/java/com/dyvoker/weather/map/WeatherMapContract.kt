package com.dyvoker.weather.map

import com.dyvoker.weather.common.BaseContract
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint

class WeatherMapContract {

    interface Presenter : BaseContract.Presenter<View> {
        fun updateWeatherAtPoint(point: MapPoint)
        fun goToMyLocation()
        fun addCity(cityName: String, point: MapPoint)
    }

    interface View : BaseContract.View {
        fun showWeatherAtPoint(point: MapPoint, data: CurrentWeatherData)
        fun showMyLocationWeather(point: MapPoint, data: CurrentWeatherData)
        fun showToast(text: String)
    }
}
