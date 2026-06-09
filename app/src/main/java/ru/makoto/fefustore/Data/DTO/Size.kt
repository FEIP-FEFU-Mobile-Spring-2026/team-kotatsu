package ru.makoto.fefustore.Data.DTO

import kotlinx.serialization.Serializable
import ru.makoto.fefustore.Data.Entity.ClothesSizeEntity

@Serializable
data class Size (
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

fun Size.toEntity(clothesId: String): ClothesSizeEntity = ClothesSizeEntity(
    id = id,
    clothesId = clothesId,
    sizeName = name.toString()
)