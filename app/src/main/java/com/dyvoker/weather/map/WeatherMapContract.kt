package com.dyvoker.weather.map

import com.dyvoker.weather.common.BaseContract
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint
import com.google.android.gms.maps.model.LatLng

class WeatherMapContract {

    interface Presenter : BaseContract.Presenter<View> {
        fun updateWeatherAtPoint(coordinates: MapPoint)
        fun goToMyLocation()
    }

    interface View : BaseContract.View {
        fun showWeatherAtPoint(data: CurrentWeatherData, point: MapPoint)
        fun showLocation(point: LatLng)
    }
}
