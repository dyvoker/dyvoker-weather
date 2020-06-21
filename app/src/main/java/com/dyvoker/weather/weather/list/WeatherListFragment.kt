package com.dyvoker.weather.weather.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dyvoker.weather.R
import com.dyvoker.weather.common.App
import com.dyvoker.weather.common.getCurrentLocale
import com.dyvoker.weather.core.DarkSkyUtils
import com.dyvoker.weather.common.rv.RVAdapter
import com.dyvoker.weather.common.rv.RVArrayListItemsProvider
import com.dyvoker.weather.common.rv.VHBinder
import com.dyvoker.weather.common.rv.VHFactory
import com.dyvoker.weather.common.toCelsiusInt
import com.dyvoker.weather.core.data.DailyWeatherData
import com.dyvoker.weather.core.data.MapPoint
import com.dyvoker.weather.databinding.FragmentWeatherListBinding
import com.dyvoker.weather.di.component.DaggerWeatherListScreenComponent
import java.util.*
import javax.inject.Inject

class WeatherListFragment : Fragment(), WeatherListContract.View {

    private val calendar = Calendar.getInstance()
    private var _binding: FragmentWeatherListBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var presenter: WeatherListContract.Presenter
    private lateinit var coordinates: MapPoint
    private val itemsProvider = RVArrayListItemsProvider<DailyWeatherData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments == null) {
            // A straight crash will motivate developers to write better code!
            throw RuntimeException("arguments is not set for this WeatherListFragment!")
        }
        coordinates = MapPoint(
            arguments!!.getDouble(latitudeKey),
            arguments!!.getDouble(longitudeKey)
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        // Here I'm using my code for convenient work with RecyclerView with only one viewType.
        binding.recyclerView.adapter = RVAdapter(
            object : VHFactory<WeatherVH> {
                override fun create(parent: ViewGroup, viewType: Int): WeatherVH {
                    val weatherItem: View = layoutInflater
                        .inflate(R.layout.item_weather, parent, false)
                    return WeatherVH(
                        weatherItem,
                        weatherItem.findViewById(R.id.icon),
                        weatherItem.findViewById(R.id.temperature),
                        weatherItem.findViewById(R.id.date)
                    )
                }
            },
            object : VHBinder<WeatherVH, DailyWeatherData> {
                @SuppressLint("SetTextI18n")
                override fun onBind(holder: WeatherVH, item: DailyWeatherData) {
                    val low = item.temperatureLow.toCelsiusInt()
                    val high = item.temperatureHigh.toCelsiusInt()
                    holder.temperature.text = "${low}..${high}°C"
                    holder.icon.setImageResource(DarkSkyUtils.getIconId(item.icon))
                    calendar.timeInMillis = item.timestamp * 1000L
                    val locale = requireContext().getCurrentLocale()
                    val dayOfWeek = String.format(locale, "%1\$ta", calendar).toUpperCase(locale)
                    val dayOfMonth = String.format(locale, "%1\$te", calendar)
                    val month = String.format(locale, "%1\$tB", calendar).capitalize(locale)
                    holder.date.text = "$dayOfWeek, $dayOfMonth $month"
                }
            },
            itemsProvider
        )

        // DI.
        val appComponent = App.appComponent()
        DaggerWeatherListScreenComponent.factory().create(appComponent).inject(this)

        presenter.attach(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.updateForecast(coordinates)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showForecast(list: List<DailyWeatherData>) {
        itemsProvider.clear()
        itemsProvider.addAll(list)
    }

    companion object {
        const val latitudeKey = "latitude_key"
        const val longitudeKey = "longitude_key"
    }
}