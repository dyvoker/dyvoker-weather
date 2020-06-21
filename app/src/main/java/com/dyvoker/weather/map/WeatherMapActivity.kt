package com.dyvoker.weather.map

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dyvoker.weather.R
import com.dyvoker.weather.common.App
import com.dyvoker.weather.common.getCurrentLocale
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.databinding.ActivityWeatherMapBinding
import com.dyvoker.weather.di.component.DaggerWeatherMapScreenComponent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.toolbar.view.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject


class WeatherMapActivity : AppCompatActivity(), WeatherMapContract.View, OnMapReadyCallback {

    @Inject
    lateinit var presenter: WeatherMapContract.Presenter

    private lateinit var binding: ActivityWeatherMapBinding
    private lateinit var map: GoogleMap
    private lateinit var weatherMarker: Marker
    private lateinit var weatherMarketRenderer: WeatherMarkerRenderer
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar.
        with(binding.toolbarWidget.toolbar) {
            toolbar_title.setText(R.string.weather_map)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setOnClickListener {
                onBackPressed()
            }
        }

        // DI.
        val appComponent = App.appComponent()
        DaggerWeatherMapScreenComponent.factory().create(appComponent).inject(this)

        geocoder = Geocoder(this, getCurrentLocale())

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        presenter.attach(this)

        binding.myLocation.setOnClickListener {
            goToMyLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions
            .onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun showWeatherAtPoint(point: MapPoint, data: CurrentWeatherData) {
        showWeatherMarker(point, data)
    }

    override fun showMyLocationWeather(point: MapPoint, data: CurrentWeatherData) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(point.toLatLng(), 16.0f))
        showWeatherMarker(point, data)
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMapClickListener {
            presenter.updateWeatherAtPoint(it.toMapPoint())
        }
        map.setOnMarkerClickListener {
            val point = it.position.toMapPoint()
            val list = geocoder.getFromLocation(point.latitude, point.longitude, 1)
            if (list.isNotEmpty()) {
                val address = list.first()
                var city = when {
                    address.locality != null -> address.locality
                    address.adminArea != null -> "${address.adminArea} (${String.format("%.1f", point.latitude)};${String.format("%.1f", point.longitude)})"
                    address.countryName != null -> "${address.countryName} (${String.format("%.1f", point.latitude)};${String.format("%.1f", point.longitude)})"
                    else -> "(${String.format("%.1f", point.latitude)};${String.format("%.1f", point.longitude)})"
                }
                presenter.addCity(city, point)
                return@setOnMarkerClickListener true
            }
            return@setOnMarkerClickListener false
        }
    }

    private fun showWeatherMarker(point: MapPoint, data: CurrentWeatherData) {
        if (this::weatherMarketRenderer.isInitialized) {
            weatherMarketRenderer.setCurrentWeatherData(data)
        } else {
            weatherMarketRenderer = WeatherMarkerRenderer(this, data)
        }
        val icon = BitmapDescriptorFactory.fromBitmap(
            // TODO Need correct size.
            weatherMarketRenderer.renderBitmap(600)
        )
        if (this::weatherMarker.isInitialized) {
            with(weatherMarker) {
                this@with.position = point.toLatLng()
                setIcon(icon)
            }
        } else {
            weatherMarker = map.addMarker(MarkerOptions().position(point.toLatLng()).icon(icon))
        }
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION_PERMISSION)
    private fun goToMyLocation() {
        if (hasLocationPermission()) {
            presenter.goToMyLocation()
        } else {
            // Ask for location permission.
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_location),
                RC_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)

    companion object {
        private const val RC_LOCATION_PERMISSION = 42
    }
}