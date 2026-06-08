package ru.makoto.fefustore.Data.DTO

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val id: Int = 0,
    val clothes: Clothes,
    val amount: Int
)