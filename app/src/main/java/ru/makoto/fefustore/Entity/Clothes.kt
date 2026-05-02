package ru.makoto.fefustore.Entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class ProductsData(
    @SerialName("categories") val categories: List<Category>,
    @SerialName("items") val products: List<Clothes>
)

@Serializable
data class Clothes(
    val id: String,
    @SerialName("name") val title: String,
    @SerialName("shortDescription") val description: String,
    val longDescription: String,
    @SerialName("priceInKopecks") val price: Int,
    @SerialName("imageUrl") val img: String,
    @SerialName("categoryId") val category: String,
    val sizes: List<Size>

)

@Serializable
data class Size(
    val id : String,
    val name : SizeName
)

@Serializable
enum class SizeName {
    XXL,
    XL,
    L,
    M,
    S,
    XS,
    XXS
}


// Добавлена глобальная viewModel которая сохраняет состояние между Activity см. в MainActivity