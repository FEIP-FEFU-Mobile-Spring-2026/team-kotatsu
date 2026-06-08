package ru.makoto.fefustore.Data.Entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "selected_category",
    foreignKeys = [
        ForeignKey(
            entity=CategoryEntity::class,
            parentColumns=["id"],
            childColumns=["categoryId"]
        )
    ],
)
data class SelectedCategoryEntity(
    @PrimaryKey
    val categoryId: String,
    val isSelected: Boolean = false
)