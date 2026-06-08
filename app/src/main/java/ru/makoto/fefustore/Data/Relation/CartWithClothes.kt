package ru.makoto.fefustore.Data.Relation

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.ForeignKey

import ru.makoto.fefustore.Data.Entity.CartEntity
import ru.makoto.fefustore.Data.Entity.ClothesEntity
import ru.makoto.fefustore.Data.Entity.toCartItem

data class CartWithClothes (
    @Embedded val cart: CartEntity,
    @Relation(
        entity = ClothesEntity::class,
        parentColumn = "clothesId",
        entityColumn = "id"
    )
    val clothes: ClothesWithDetails
)

fun CartWithClothes.toCartItem() = cart.toCartItem(clothes = clothes.toClothes())