package ru.makoto.fefustore.Data.DTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductsData(
    @SerialName("categories") val categories: List<Category>,
    @SerialName("items") val products: List<Clothes>
)