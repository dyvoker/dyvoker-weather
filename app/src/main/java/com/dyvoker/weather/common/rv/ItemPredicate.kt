package com.dyvoker.weather.common.rv

/**
 * Checks items for equality.
 */
interface ItemPredicate<I> {
    fun isEqual(item1: I, item2: I): Boolean
}

/**
 * Checks items for equality by reference.
 */
class ReferenceItemPredicate<I> : ItemPredicate<I> {
    override fun isEqual(item1: I, item2: I): Boolean =
        item1 == item2
}