package ru.makoto.fefustore.Data.Repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.makoto.fefustore.Data.DAO.*
import ru.makoto.fefustore.Data.DTO.*
import ru.makoto.fefustore.Data.Entity.*
import javax.inject.Inject
import javax.inject.Singleton

/*
**Определение**
Data access object - объекты которые представляют действия над таблицами, что я задал в Entity.

Инъекция DAO (Самый быстрый и безболезненный (рекомендуемый Google) способ предоставить DAO в репозиторий для дальнейшего
использования).
"Регистрируются" инъекции в пакете DI

**Определение**
Доменные объекты (Domain objects) - объекты которые являются частью бизнес логики. Мы с ними работаем, а потом
трансформируем в DTO (Data Transfer Object) если нужно передать их во фронт
Когда DAO получает/отдаёт данные, то мы получаем/отдаём их как Entities.
Но с Entities работать нельзя! (ведь это идеальные форматные данные бд)
Мы сразу переделываем их в доменные... Но у нас слишком простая система.

Поэтому у нас Domain Data = DTO

 */
@Singleton
class StoreRepository @Inject constructor(
    private val categoryDAO: CategoryDAO,
    private val clothesDAO: ClothesDAO,
    private val clothesSizeDAO: ClothesSizeDAO,
    private val clothesTagDAO: ClothesTagDAO,
    private val tagDAO: TagDAO
) {
    fun getAllCategories(): Flow<List<Category>> = categoryDAO.getAll().map {
        it.map { categoryEntity ->
            categoryEntity.toCategory()
        }
    }
    fun getAllClothes(): Flow<List<Clothes>> = clothesDAO.getAll().map {
        it.map { clothesEntity ->
            val clothesDto = clothesEntity.toClothes()
            clothesDto.sizes = clothesSizeDAO.getSizesByClothesId(clothesDto.id).first()
                .map { size ->
                    Size(size.id, SizeName.valueOf(size.sizeName))
                }
            clothesDto.tags = clothesTagDAO.getTagsClothesByClothesId(clothesDto.id).first()
                .map { clothesTag ->
                    tagDAO.getTagById(clothesTag.tagId).first().toTagString()
                }
            clothesDto
        }
    }

    suspend fun addCategory(category: CategoryEntity) = categoryDAO.insert(category)
    suspend fun addClothes(clothes: ClothesEntity) = clothesDAO.insert(clothes)
    suspend fun addTag(tag: TagEntity) = tagDAO.insert(tag)

    suspend fun addClothesSize(clothesSize: ClothesSizeEntity) = clothesSizeDAO.insert(clothesSize)
    suspend fun setClothesTag(clothes: ClothesEntity, tag: TagEntity) = clothesTagDAO.insert(
        ClothesTagEntity(
            clothesId = clothes.id,
            tagId = tag.id
        )
    )
}