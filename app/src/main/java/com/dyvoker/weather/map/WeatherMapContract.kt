package com.dyvoker.weather.map

import com.dyvoker.weather.common.BaseContract
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint

class WeatherMapContract {

    interface Presenter : BaseContract.Presenter<View> {
        fun updateWeather(coordinates: MapPoint)
    }

    interface View : BaseContract.View {
        fun showWeather(data: CurrentWeatherData)
    }
}
