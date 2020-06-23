package com.dyvoker.weather.map

import com.dyvoker.weather.common.BaseContract
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint

class WeatherMapContract {

    interface Presenter : BaseContract.Presenter<View> {
        fun onGoogleMapReady()
        fun onMapClick(point: MapPoint)
        fun onMarkerClick(point: MapPoint): Boolean
        fun onGoogleMapsMyLocationButtonClick()
        fun onOwnMyLocationButtonClick()
        fun locationPermissionGranted()
    }

    interface View : BaseContract.View {
        fun moveGoogleMapsCamera(point: MapPoint)
        fun showWeatherAtPoint(point: MapPoint, data: CurrentWeatherData)
        fun showCityAdded(city: String)
        fun showLoadingError()
        fun showGeolocationError()
        fun setGoogleMapsMyLocationButtonVisible(visible: Boolean)
        fun setOwnMyLocationButtonVisible(visible: Boolean)
        fun requestLocationPermission()
    }
}
