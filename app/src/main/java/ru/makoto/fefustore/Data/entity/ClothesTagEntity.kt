package ru.makoto.fefustore.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "clothes_tag",
    primaryKeys = ["clothesId", "tagId"],
    foreignKeys = [
        ForeignKey(
            entity = ClothesEntity::class,
            parentColumns = ["id"],
            childColumns = ["clothesId"],
        ),
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
        ),
    ],
    indices = [
        Index("clothesId"),
        Index("tagId"),
    ],
)
data class ClothesTagEntity(
    val clothesId: String,
    val tagId: String,
)
