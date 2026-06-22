package ru.makoto.fefustore.data.dto

import kotlinx.serialization.Serializable
import ru.makoto.fefustore.data.entity.ClothesSizeEntity

@Serializable
data class Size(
    val id: String,
    val name: SizeName,
)

@Serializable
enum class SizeName {
    XXL,
    XL,
    L,
    M,
    S,
    XS,
    XXS,
}

fun Size.toEntity(clothesId: String): ClothesSizeEntity =
    ClothesSizeEntity(
        id = id,
        clothesId = clothesId,
        sizeName = name.toString(),
    )
