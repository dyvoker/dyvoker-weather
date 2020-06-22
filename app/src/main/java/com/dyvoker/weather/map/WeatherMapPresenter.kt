package com.dyvoker.weather.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.dyvoker.weather.common.getCurrentLocale
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.repository.GlobalRepository
import com.dyvoker.weather.core.repository.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WeatherMapPresenter(
    context: Context,
    private val repository: WeatherRepository,
    private val globalRepository: GlobalRepository
) : WeatherMapContract.Presenter {

    private lateinit var view: WeatherMapContract.View
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val geocoder = Geocoder(context, context.getCurrentLocale())

    override fun attach(view: WeatherMapContract.View) {
        this.view = view
    }

    override fun onMapClick(point: MapPoint) {
        GlobalScope.launch(Dispatchers.Main) {
            val currentWeather = repository.getCurrentWeather(point)
            view.showWeatherAtPoint(point, currentWeather)
        }
    }

    @SuppressLint("MissingPermission")
    override fun goToMyLocation() {
        fusedLocationClient.lastLocation.addOnCompleteListener {
            it.result?.run {
                GlobalScope.launch(Dispatchers.Main) {
                    val coordinates = MapPoint(latitude, longitude)
                    val currentWeather = repository.getCurrentWeather(coordinates)
                    view.showMyLocationWeather(coordinates, currentWeather)
                }
            }
        }
    }

    override fun onMarkerClick(point: MapPoint): Boolean {
        val list = geocoder.getFromLocation(point.latitude, point.longitude, 1)
        if (list.isNotEmpty()) {
            val address = list.first()
            val city = when {
                address.locality != null -> address.locality
                address.adminArea != null -> "${address.adminArea} (${String.format("%.1f", point.latitude)};${String.format("%.1f", point.longitude)})"
                address.countryName != null -> "${address.countryName} (${String.format("%.1f", point.latitude)};${String.format("%.1f", point.longitude)})"
                else -> "(${String.format("%.1f", point.latitude)};${String.format("%.1f", point.longitude)})"
            }
            globalRepository.addCity(city, point)
            view.showCityAdded(city)
            return true
        }
        return false
    }
}