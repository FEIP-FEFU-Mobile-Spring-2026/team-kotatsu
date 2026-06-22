package ru.makoto.fefustore.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.makoto.fefustore.data.dto.Category

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
)

fun CategoryEntity.toCategory(): Category =
    Category(
        id = id,
        name = name,
    )
