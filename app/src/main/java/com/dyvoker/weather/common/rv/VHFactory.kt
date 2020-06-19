package com.dyvoker.weather.common.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface VHFactory<VH : RecyclerView.ViewHolder> {

    fun create(parent: ViewGroup, viewType: Int): VH
}