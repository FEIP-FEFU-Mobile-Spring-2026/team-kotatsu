package ru.makoto.fefustore.Data.Entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.makoto.fefustore.Data.DTO.CartItem
import ru.makoto.fefustore.Data.DTO.Clothes
import ru.makoto.fefustore.Data.DTO.Size

@Entity(
    tableName = "cart",
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
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val clothesId: String,
    val sizeId: String? = null,
    val amount: Int
)

fun CartEntity.toCartItem(clothes: Clothes, selectedSize: Size?) = CartItem(
    clothes = clothes,
    amount = amount,
    selectedSize = selectedSize
)