package ru.makoto.fefustore

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.makoto.fefustore.data.dto.Category
import ru.makoto.fefustore.data.dto.Clothes
import ru.makoto.fefustore.data.dto.Size
import ru.makoto.fefustore.data.dto.SizeName
import ru.makoto.fefustore.data.dto.toEntity

class MappingTest {
    @Test
    fun `Category toEntity maps fields correctly`() {
        val dtoCategory = Category(id = "cat_shoes", name = "Обувь")
        val entity = dtoCategory.toEntity()

        assertEquals("cat_shoes", entity.id)
        assertEquals("Обувь", entity.name)
    }

    @Test
    fun `Clothes toEntity maps crucial data fields correctly`() {
        val dtoClothes =
            Clothes(
                id = "item_001",
                title = "Худи",
                description = "Стильный худи",
                longDescription = "Описание товара",
                price = 499000,
                img = "url_to_image",
                category = "cat_outerwear",
                material = "100% хлопок",
                weight = "500 г",
                season = "Winter",
                countryOfOrigin = "China",
                sizes = emptyList(),
                tags = emptyList(),
            )

        val entity = dtoClothes.toEntity()

        assertEquals("item_001", entity.id)
        assertEquals("Худи", entity.title)
        assertEquals(499000, entity.price)
        assertEquals("cat_outerwear", entity.categoryId)
        assertEquals("Стильный худи", entity.description)
    }

    @Test
    fun `Size toEntity maps fields and enum name correctly`() {
        val dtoSize = Size(id = "size_004_xxs", name = SizeName.XXS)
        val associatedClothesId = "item_001"

        val entity = dtoSize.toEntity(clothesId = associatedClothesId)

        assertEquals("size_004_xxs", entity.id)
        assertEquals("item_001", entity.clothesId)
        assertEquals("XXS", entity.sizeName)
    }
}
