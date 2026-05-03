package ru.makoto.fefustore.Entity

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object Cart {
    private val _cart = MutableStateFlow<Map<String, Int>>(emptyMap())
    val cartItems: StateFlow<Map<String, Int>> = _cart.asStateFlow()

    fun addItem(itemId: String) {
        _cart.update { currentMap ->
            val currentCount = (currentMap[itemId] ?: 0) + 1
            currentMap + (itemId to currentCount)
        }
    }

    fun removeItem(itemId: String) {
        _cart.update { currentMap ->
            val currentCount = currentMap[itemId] ?: return@update currentMap
            if (currentCount > 1) {
                currentMap + (itemId to currentCount - 1)
            } else {
                currentMap - itemId
            }
        }
    }
}