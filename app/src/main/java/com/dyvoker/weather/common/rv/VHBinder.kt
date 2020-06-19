package com.dyvoker.weather.common.rv

import androidx.recyclerview.widget.RecyclerView

interface VHBinder<VH : RecyclerView.ViewHolder, I> {

    fun onBind(holder: VH, item: I)
}