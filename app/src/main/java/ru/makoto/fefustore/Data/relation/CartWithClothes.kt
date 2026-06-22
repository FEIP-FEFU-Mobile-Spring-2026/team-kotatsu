package ru.makoto.fefustore.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import ru.makoto.fefustore.data.dto.CartItem
import ru.makoto.fefustore.data.entity.CartEntity
import ru.makoto.fefustore.data.entity.ClothesEntity
import ru.makoto.fefustore.data.entity.toCartItem

data class CartWithClothes(
    @Embedded val cart: CartEntity,
    @Relation(
        entity = ClothesEntity::class,
        parentColumn = "clothesId",
        entityColumn = "id",
    )
    val clothes: ClothesWithDetails,
)

fun CartWithClothes.toCartItem(): CartItem {
    val domainClothes = clothes.toClothes()

    val selectedSize = domainClothes.sizes.find { it.id == cart.sizeId }

    return cart.toCartItem(clothes = domainClothes, selectedSize = selectedSize)
}
