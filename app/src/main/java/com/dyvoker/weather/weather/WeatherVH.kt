package com.dyvoker.weather.weather

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeatherVH(
    itemView: View,
    val icon: ImageView,
    val temperature: TextView,
    val date: TextView
) : RecyclerView.ViewHolder(itemView)