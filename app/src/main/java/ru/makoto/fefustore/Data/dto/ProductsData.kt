package ru.makoto.fefustore.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductsData(
    @SerialName("categories") val categories: List<Category>,
    @SerialName("items") val products: List<Clothes>,
)
