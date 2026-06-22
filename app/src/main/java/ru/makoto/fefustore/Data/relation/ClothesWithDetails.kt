package ru.makoto.fefustore.data.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.makoto.fefustore.data.entity.ClothesEntity
import ru.makoto.fefustore.data.entity.ClothesSizeEntity
import ru.makoto.fefustore.data.entity.ClothesTagEntity
import ru.makoto.fefustore.data.entity.TagEntity
import ru.makoto.fefustore.data.entity.toClothes
import ru.makoto.fefustore.data.entity.toSize
import ru.makoto.fefustore.data.entity.toTagString

data class ClothesWithDetails(
    @Embedded val clothes: ClothesEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "clothesId",
    )
    val sizes: List<ClothesSizeEntity>,
    @Relation(
        entity = TagEntity::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy =
            Junction(
                value = ClothesTagEntity::class,
                entityColumn = "tagId",
                parentColumn = "clothesId",
            ),
    )
    val tags: List<TagEntity>,
)

fun ClothesWithDetails.toClothes() =
    clothes.toClothes(
        sizes =
            sizes.map { sizeEntity ->
                sizeEntity.toSize()
            },
        tags =
            tags.map { tagEntity ->
                tagEntity.toTagString()
            },
    )
