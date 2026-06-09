package ru.makoto.fefustore.Data.Entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.ForeignKey

@Entity(
    tableName = "clothes_tag",
    primaryKeys = ["clothesId", "tagId"],
    foreignKeys = [
        ForeignKey(
            entity = ClothesEntity::class,
            parentColumns = ["id"],
            childColumns = ["clothesId"]
        ),
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tagId"]
        )
    ],
    indices = [
        Index("clothesId"),
        Index("tagId")
    ]
)
data class ClothesTagEntity(
    val clothesId: String,
    val tagId: String
)