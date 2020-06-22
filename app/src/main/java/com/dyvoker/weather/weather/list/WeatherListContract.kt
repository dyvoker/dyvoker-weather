package com.dyvoker.weather.weather.list

import com.dyvoker.weather.common.BaseContract
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.data.DailyWeatherData

class WeatherListContract {

    interface Presenter : BaseContract.Presenter<View> {
        fun initCoordinates(point: MapPoint)
        fun updateForecast()
    }

    interface View : BaseContract.View {
        fun showForecast(list: List<DailyWeatherData>)
    }
}
