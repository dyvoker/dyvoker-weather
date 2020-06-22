package com.dyvoker.weather.map

import com.dyvoker.weather.common.BaseContract
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint

class WeatherMapContract {

    interface Presenter : BaseContract.Presenter<View> {
        fun onMapClick(point: MapPoint)
        fun goToMyLocation()
        fun onMarkerClick(point: MapPoint): Boolean
    }

    interface View : BaseContract.View {
        fun showWeatherAtPoint(point: MapPoint, data: CurrentWeatherData)
        fun showMyLocationWeather(point: MapPoint, data: CurrentWeatherData)
        fun showCityAdded(city: String)
    }
}
