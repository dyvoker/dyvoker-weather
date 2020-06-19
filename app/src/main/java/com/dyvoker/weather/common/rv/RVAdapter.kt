package com.dyvoker.weather.common.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class RVAdapter<VH : RecyclerView.ViewHolder, I>(
    private val vhFactory: VHFactory<VH>,
    private val binder: VHBinder<VH, I>,
    private val itemsProvider: RVItemsProvider<I>
) : RecyclerView.Adapter<VH>() {

    init {
        itemsProvider.setNotifyObserver { notifyDataSetChanged() }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        vhFactory.create(parent, viewType)

    override fun onBindViewHolder(holder: VH, position: Int) {
        binder.onBind(holder, itemsProvider.get(position))
    }

    override fun getItemCount(): Int =
        itemsProvider.getCount()
}