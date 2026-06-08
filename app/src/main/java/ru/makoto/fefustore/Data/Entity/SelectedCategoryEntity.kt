package ru.makoto.fefustore.Data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "selected_category"
)
data class SelectedCategoryEntity(
    @PrimaryKey
    val categoryId: String,
    val isSelected: Boolean = false
)