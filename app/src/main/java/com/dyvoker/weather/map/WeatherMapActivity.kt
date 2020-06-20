package com.dyvoker.weather.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dyvoker.weather.R
import com.dyvoker.weather.common.App
import com.dyvoker.weather.core.data.CurrentWeatherData
import com.dyvoker.weather.databinding.ActivityWeatherMapBinding
import com.dyvoker.weather.di.component.DaggerWeatherMapScreenComponent
import kotlinx.android.synthetic.main.toolbar.view.*
import javax.inject.Inject

class WeatherMapActivity : AppCompatActivity(), WeatherMapContract.View {

    @Inject
    lateinit var presenter: WeatherMapContract.Presenter

    private lateinit var binding: ActivityWeatherMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherMapBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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

        presenter.attach(this)
    }

    override fun showWeather(data: CurrentWeatherData) {
        // TODO
    }
}