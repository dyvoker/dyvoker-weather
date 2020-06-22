package com.dyvoker.weather.common.rv

@Suppress("unused", "MemberVisibilityCanBePrivate")
class RVArrayListItemsProvider<I>(
    private val itemPredicate: ItemPredicate<I> = ReferenceItemPredicate()
) : RVItemsProvider<I> {

    private var notifyObserver: NotifyObserver? = null
    private var onNotifyListenerList = ArrayList<OnNotifyListener>(3)
    private var items: ArrayList<I> = ArrayList()

    override fun get(position: Int): I = items[position]

    override fun getCount(): Int = items.size

    override fun setNotifyObserver(observer: NotifyObserver) {
        notifyObserver = observer
    }

    fun set(newItems: Collection<I>) {
        items = ArrayList(newItems)
        notifyChanged()
    }

    fun clear() {
        items.clear()
        notifyChanged()
    }

    fun add(item: I) {
        items.add(item)
        notifyChanged()
    }

    fun add(item: I, position: Int) {
        items.add(position, item)
        notifyChanged()
    }

    fun addAll(list: List<I>) {
        for (item in list) {
            items.add(item)
        }
        notifyChanged()
    }

    fun remove(item: I) {
        var removeIndex = -1
        items.forEachIndexed { index, current ->
            if (itemPredicate.isEqual(current, item)) {
                removeIndex = index
            }
        }
        if (removeIndex != -1) {
            remove(removeIndex)
        }
    }

    fun getAll(): ArrayList<I> = items

    fun remove(index: Int) {
        items.removeAt(index)
        notifyChanged()
    }

    fun notifyChanged() {
        notifyObserver?.invoke()
        onNotifyListenerList.forEach {
            it.onNotify(items.size)
        }
    }

    fun addOnNotifyListener(onNotifyListener: OnNotifyListener) {
        onNotifyListenerList.add(onNotifyListener)
    }

    fun removeOnNotifyListener(onNotifyListener: OnNotifyListener) {
        onNotifyListenerList.remove(onNotifyListener)
    }

    interface OnNotifyListener {
        fun onNotify(size: Int)
    }
}