package ru.makoto.fefustore.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val id: Int = 0,
    val clothes: Clothes,
    val amount: Int,
    val selectedSize: Size? = null,
)
