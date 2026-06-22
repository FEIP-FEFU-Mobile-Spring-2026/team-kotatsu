package ru.makoto.fefustore.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import ru.makoto.fefustore.data.dto.Size
import ru.makoto.fefustore.data.dto.SizeName

@Entity(
    tableName = "clothes_size",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = ClothesEntity::class,
            parentColumns = ["id"],
            childColumns = ["clothesId"],
        ),
    ],
    indices = [
        Index("clothesId"),
    ],
)
data class ClothesSizeEntity(
    val id: String,
    val clothesId: String,
    val sizeName: String,
)

fun ClothesSizeEntity.toSize(): Size =
    Size(
        id = id,
        name = SizeName.valueOf(sizeName),
    )
