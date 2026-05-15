package ru.makoto.fefustore.Data.DTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.makoto.fefustore.Data.Entity.ClothesEntity

@Serializable
data class Clothes(
    val id: String,
    @SerialName("name") val title: String,
    @SerialName("shortDescription") val description: String,
    val longDescription: String,
    @SerialName("priceInKopecks") val price: Int,
    @SerialName("imageUrl") val img: String,
    @SerialName("categoryId") val category: String,
    val material: String,
    val weight: String,
    val season: String,
    val countryOfOrigin: String,
    var sizes: List<Size>? = null,
    var tags: List<String>? = null

)

fun Clothes.toEntity() : ClothesEntity = ClothesEntity(
    id = id,
    title = title,
    description = description,
    longDescription = longDescription,
    price = price,
    img = img,
    categoryId = category,
    material = material,
    weight = weight,
    season = season,
    countryOfOrigin = countryOfOrigin
)

