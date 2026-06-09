package ru.makoto.fefustore.Data.Entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.makoto.fefustore.Data.DTO.Clothes
import ru.makoto.fefustore.Data.DTO.Size

@Entity(
    tableName = "clothes",
    foreignKeys = [
        ForeignKey(
            entity=CategoryEntity::class,
            parentColumns=["id"],
            childColumns=["categoryId"],
            onDelete= ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("categoryId")
    ]
)
data class ClothesEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val longDescription: String,
    val price: Int,
    val img: String,
    val categoryId: String,
    val material: String,
    val weight: String,
    val season: String,
    val countryOfOrigin: String
)

fun ClothesEntity.toClothes(sizes: List<Size>, tags: List<String>) : Clothes = Clothes(
    id = id,
    title = title,
    description = description,
    longDescription = longDescription,
    price = price,
    img = img,
    category = categoryId,
    material = material,
    weight = weight,
    season = season,
    countryOfOrigin = countryOfOrigin,
    sizes = sizes,
    tags = tags
)