package ru.makoto.fefustore

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap

object CartState {
    val cart: SnapshotStateMap<Int, Int> = mutableStateMapOf()

    fun addItem(itemId: Int) {
        cart[itemId] = (cart[itemId] ?: 0) + 1
    }

    fun removeItem(itemId: Int) {
        val current = cart[itemId] ?: 0
        if (current > 1) {
            cart[itemId] = current - 1
        } else {
            cart.remove(itemId)
        }
    }

    fun getCount(itemId: Int): Int {
        return cart[itemId] ?: 0
    }
}