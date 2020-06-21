package com.dyvoker.weather.map

import android.annotation.SuppressLint
import android.content.Context
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.repository.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WeatherMapPresenter(
    context: Context,
    private val repository: WeatherRepository
) : WeatherMapContract.Presenter {

    private lateinit var view: WeatherMapContract.View
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun attach(view: WeatherMapContract.View) {
        this.view = view
    }

    override fun updateWeather(coordinates: MapPoint) {
        GlobalScope.launch(Dispatchers.Main) {
            val currentWeather = repository.getCurrentWeather(coordinates)
            view.showWeather(currentWeather)
        }
    }

    @SuppressLint("MissingPermission")
    override fun goToMyLocation() {
        fusedLocationClient.lastLocation.addOnCompleteListener {
            it.result?.run {
                val point = LatLng(latitude, longitude)
                view.showLocation(point)
            }
        }
    }
}