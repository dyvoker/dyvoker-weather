package com.dyvoker.weather.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dyvoker.weather.R
import com.dyvoker.weather.common.WeatherIconUtils
import com.dyvoker.weather.core.data.WeatherItemData
import com.dyvoker.weather.databinding.FragmentWeatherListBinding
import com.dyvoker.weather.common.rv.RVAdapter
import com.dyvoker.weather.common.rv.RVArrayListItemsProvider
import com.dyvoker.weather.common.rv.VHBinder
import com.dyvoker.weather.common.rv.VHFactory

class WeatherListFragment : Fragment() {

    private var _binding: FragmentWeatherListBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    // TODO Move to repository.
    private val itemsProviderTemp = RVArrayListItemsProvider<WeatherItemData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Here I'm using my code for convenient work with RecyclerView with only one viewType.
        binding.recyclerView.adapter = RVAdapter(
            object : VHFactory<WeatherVH> {
                override fun create(parent: ViewGroup, viewType: Int): WeatherVH {
                    val lapView: View = layoutInflater
                        .inflate(R.layout.item_weather, parent, false)
                    return WeatherVH(
                        lapView,
                        lapView.findViewById(R.id.icon),
                        lapView.findViewById(R.id.temperature),
                        lapView.findViewById(R.id.date)
                    )
                }
            },
            object : VHBinder<WeatherVH, WeatherItemData> {
                @SuppressLint("SetTextI18n")
                override fun onBind(holder: WeatherVH, item: WeatherItemData) {
                    holder.temperature.text = "${item.temperature}°C"
                    holder.icon.setImageResource(WeatherIconUtils.getResId(item.icon))
                    // TODO Also need to change date.
                }
            },
            itemsProviderTemp
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}