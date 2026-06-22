package ru.makoto.fefustore.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "selected_category",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
        ),
    ],
)
data class SelectedCategoryEntity(
    @PrimaryKey
    val categoryId: String,
    val isSelected: Boolean = false,
)
