package ru.makoto.fefustore.Data.Relation

import androidx.room.Embedded
import androidx.room.Relation
import ru.makoto.fefustore.Data.Entity.CartEntity
import ru.makoto.fefustore.Data.Entity.ClothesEntity
import ru.makoto.fefustore.Data.Entity.toCartItem
import ru.makoto.fefustore.Data.DTO.CartItem

data class CartWithClothes(
    @Embedded val cart: CartEntity,
    @Relation(
        entity = ClothesEntity::class,
        parentColumn = "clothesId",
        entityColumn = "id"
    )
    val clothes: ClothesWithDetails
)

fun CartWithClothes.toCartItem(): CartItem {
    val domainClothes = clothes.toClothes()

    val selectedSize = domainClothes.sizes.find { it.id == cart.sizeId }

    return cart.toCartItem(clothes = domainClothes, selectedSize = selectedSize)
}