package com.dyvoker.weather.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dyvoker.weather.R
import com.dyvoker.weather.common.App
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.databinding.ActivityWeatherMapBinding
import com.dyvoker.weather.di.component.DaggerWeatherMapScreenComponent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.toolbar.view.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject


class WeatherMapActivity : AppCompatActivity(), WeatherMapContract.View, OnMapReadyCallback {

    @Inject
    lateinit var presenter: WeatherMapContract.Presenter

    private lateinit var binding: ActivityWeatherMapBinding
    private lateinit var map: GoogleMap

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

    override fun showWeather(data: CurrentWeatherData) {
        // TODO
    }

    override fun showLocation(point: LatLng) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 16.0f))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
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