package ru.makoto.fefustore.Data.Relation

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.Junction
import ru.makoto.fefustore.Data.Entity.ClothesEntity
import ru.makoto.fefustore.Data.Entity.ClothesSizeEntity
import ru.makoto.fefustore.Data.Entity.TagEntity
import ru.makoto.fefustore.Data.Entity.ClothesTagEntity
import ru.makoto.fefustore.Data.Entity.toClothes
import ru.makoto.fefustore.Data.Entity.toSize
import ru.makoto.fefustore.Data.Entity.toTagString


data class ClothesWithDetails(
    @Embedded val clothes: ClothesEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "clothesId"
    )
    val sizes: List<ClothesSizeEntity>,

    @Relation(
        entity = TagEntity::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = ClothesTagEntity::class,
            entityColumn = "tagId",
            parentColumn = "clothesId"
        )
    )
    val tags: List<TagEntity>
)

fun ClothesWithDetails.toClothes() = clothes.toClothes(
    sizes = sizes.map { sizeEntity ->
        sizeEntity.toSize()
    },
    tags = tags.map { tagEntity ->
        tagEntity.toTagString()
    }
)