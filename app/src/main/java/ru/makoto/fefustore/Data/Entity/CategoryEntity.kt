package ru.makoto.fefustore.Data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.makoto.fefustore.Data.DTO.Category

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String
)


fun CategoryEntity.toCategory() : Category = Category(
    id = id,
    name = name
)