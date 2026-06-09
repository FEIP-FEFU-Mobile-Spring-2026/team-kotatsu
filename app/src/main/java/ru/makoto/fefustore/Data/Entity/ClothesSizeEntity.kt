package ru.makoto.fefustore.Data.Entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.ForeignKey
import ru.makoto.fefustore.Data.DTO.Size
import ru.makoto.fefustore.Data.DTO.SizeName

@Entity(
    tableName = "clothes_size",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = ClothesEntity::class,
            parentColumns = ["id"],
            childColumns = ["clothesId"]
        )
    ],
    indices = [
        Index("clothesId")
    ]
)
data class ClothesSizeEntity(
    val id: String,
    val clothesId: String,
    val sizeName: String
)

fun ClothesSizeEntity.toSize() : Size = Size(
    id = id,
    name = SizeName.valueOf(sizeName)
)