package com.dyvoker.weather.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dyvoker.weather.R
import com.dyvoker.weather.databinding.ActivityWeatherMapBinding
import kotlinx.android.synthetic.main.toolbar.view.*

class WeatherMapActivity : AppCompatActivity() {

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
    }
}