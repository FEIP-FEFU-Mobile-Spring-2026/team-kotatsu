package ru.makoto.fefustore.Data.DTO

import kotlinx.serialization.Serializable
import ru.makoto.fefustore.Data.Entity.CategoryEntity

@Serializable
data class Category(
    val id: String,
    val name: String
)

fun Category.toEntity() : CategoryEntity = CategoryEntity(
    id = id,
    name = name
)
