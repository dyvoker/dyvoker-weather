package com.dyvoker.weather.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.dyvoker.weather.R
import com.dyvoker.weather.common.App
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
        DaggerWeatherMapScreenComponent.factory().create(App.appComponent).inject(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        presenter.attach(this)

        binding.myLocation.setOnClickListener {
            presenter.onOwnMyLocationButtonClick()
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

    @SuppressLint("MissingPermission")
    override fun setGoogleMapsMyLocationButtonVisible(visible: Boolean) {
        map.isMyLocationEnabled = visible
    }

    override fun setOwnMyLocationButtonVisible(visible: Boolean) {
        binding.myLocation.isVisible = visible
    }

    override fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.rationale_location),
            RC_LOCATION_PERMISSION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    @AfterPermissionGranted(RC_LOCATION_PERMISSION)
    private fun locationPermissionGranted() {
        presenter.locationPermissionGranted()
    }

    override fun showWeatherAtPoint(point: MapPoint, data: CurrentWeatherData) {
        showWeatherMarker(point, data)
    }

    override fun moveGoogleMapsCamera(point: MapPoint) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(point.toLatLng(), 16.0f))
    }

    override fun showCityAdded(city: String) {
        val added = resources.getString(R.string.city_added)
        Toast.makeText(this, "$city $added", Toast.LENGTH_LONG).show()
    }

    override fun showLoadingError() {
        Toast.makeText(this, R.string.loading_error, Toast.LENGTH_LONG).show()
    }

    override fun showGeolocationError() {
        Toast.makeText(this, R.string.geolocation_error, Toast.LENGTH_LONG).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMyLocationButtonClickListener {
            presenter.onGoogleMapsMyLocationButtonClick()
            false
        }
        map.setOnMapClickListener {
            presenter.onMapClick(it.toMapPoint())
        }
        map.setOnMarkerClickListener {
            presenter.onMarkerClick(it.position.toMapPoint())
        }
        presenter.onGoogleMapReady()
    }

    private fun showWeatherMarker(point: MapPoint, data: CurrentWeatherData) {
        if (this::weatherMarketRenderer.isInitialized) {
            weatherMarketRenderer.setCurrentWeatherData(data)
        } else {
            weatherMarketRenderer = WeatherMarkerRenderer(this, data)
        }
        val icon = BitmapDescriptorFactory.fromBitmap(
            weatherMarketRenderer.renderBitmap(px(200f).toInt())
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

    private fun px(dp: Float) = dp * resources.displayMetrics.density

    companion object {
        private const val RC_LOCATION_PERMISSION = 42
    }
}