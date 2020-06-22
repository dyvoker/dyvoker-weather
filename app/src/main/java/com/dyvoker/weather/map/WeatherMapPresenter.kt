package com.dyvoker.weather.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.dyvoker.weather.common.getCurrentLocale
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.core.data.Resource
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
            val weather = repository.getCurrentWeather(point)
            when (weather.status) {
                Resource.Status.SUCCESS -> view.showWeatherAtPoint(point, weather.data!!)
                Resource.Status.ERROR -> {} //TODO
                Resource.Status.LOADING -> {} //TODO
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun goToMyLocation() {
        fusedLocationClient.lastLocation.addOnCompleteListener {
            it.result?.run {
                GlobalScope.launch(Dispatchers.Main) {
                    val coordinates = MapPoint(latitude, longitude)
                    val weather = repository.getCurrentWeather(coordinates)
                    when (weather.status) {
                        Resource.Status.SUCCESS -> view.showMyLocationWeather(coordinates, weather.data!!)
                        Resource.Status.ERROR -> {} //TODO
                        Resource.Status.LOADING -> {} //TODO
                    }
                }
            }
        }
    }

    override fun onMarkerClick(point: MapPoint): Boolean {
        val list = geocoder.getFromLocation(point.latitude, point.longitude, 1)
        if (list.isNotEmpty()) {
            val address = list.first()
            val latitude = String.format("%.1f", point.latitude)
            val longitude = String.format("%.1f", point.longitude)
            val city = when {
                address.locality != null -> address.locality
                address.adminArea != null -> "${address.adminArea} ($latitude;$longitude)"
                address.countryName != null -> "${address.countryName} ($latitude;$longitude)"
                else -> "($latitude;$longitude)"
            }
            GlobalScope.launch(Dispatchers.Main) {
                globalRepository.addCity(city, point)
            }
            view.showCityAdded(city)
            return true
        }
        return false
    }
}