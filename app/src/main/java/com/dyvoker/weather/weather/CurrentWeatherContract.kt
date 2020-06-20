package com.dyvoker.weather.weather

import com.dyvoker.weather.common.BaseContract
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint

class CurrentWeatherContract {

    interface Presenter : BaseContract.Presenter<View> {
        fun updateWeather(coordinates: MapPoint)
    }

    interface View : BaseContract.View {
        fun showWeather(data: CurrentWeatherData)
    }
}
