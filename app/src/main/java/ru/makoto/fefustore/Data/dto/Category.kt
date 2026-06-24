package ru.makoto.fefustore.data.dto

import kotlinx.serialization.Serializable
import ru.makoto.fefustore.data.entity.CategoryEntity

@Serializable
data class Category(
    val id: String,
    val name: String,
)

fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        id = id,
        name = name,
    )
