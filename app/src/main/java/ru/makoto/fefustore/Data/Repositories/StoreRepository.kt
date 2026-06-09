package ru.makoto.fefustore.Data.Repositories

import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.makoto.fefustore.Data.DAO.*
import ru.makoto.fefustore.Data.DTO.*
import ru.makoto.fefustore.Data.Entity.*
import ru.makoto.fefustore.Data.Relation.*
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
    private val tagDAO: TagDAO,
    private val cartDAO: CartDAO,
    private val selectedCategoryDAO: SelectedCategoryDAO
) {
    fun getAllCategories(): Flow<List<Category>> = categoryDAO.getAll().map {
        it.map { categoryEntity ->
            categoryEntity.toCategory()
        }
    }
    fun getAllClothes(): Flow<List<Clothes>> = clothesDAO.getAll().asFlow().map {
        it.map { clothesWithDetailsEntity ->
            clothesWithDetailsEntity.toClothes()
        }
    }

    fun getAllClothesInCart(): Flow<List<CartItem>> = cartDAO.getAll().map {
        it.map { cartEntity ->
            cartEntity.toCartItem()
        }
    }

    fun getCartAmount(clothesId: String): Flow<Int> = cartDAO.getItemByClothesId(clothesId).map {
        if (it.isEmpty())
            return@map 0
        return@map it[0].cart.amount
    }

    suspend fun addItemInCart(clothesId: String) {
        val cartItem = cartDAO.getItemByClothesId(clothesId).first()
        if (cartItem.isEmpty())
            cartDAO.insert(CartEntity(clothesId = clothesId, amount = 1))
        else
            cartDAO.update(cartItem[0].cart.copy(amount = cartItem[0].cart.amount + 1))
    }


    suspend fun removeItemFromCart(clothesId: String) {
        val cartItem = cartDAO.getItemByClothesId(clothesId).first()
        if (!cartItem.isEmpty()) {
            if (cartItem[0].cart.amount > 1)
                cartDAO.update(cartItem[0].cart.copy(amount = cartItem[0].cart.amount - 1))
            else
                cartDAO.delete(cartItem[0].cart)
        }
    }
    fun getSelectedCategory(): Flow<String?> = selectedCategoryDAO.getSelectedCategory().map {
        if (it.isEmpty())
            return@map null
        return@map it[0].categoryId
    }
    suspend fun selectCategory(categoryId: String) = selectedCategoryDAO.selectCategory(categoryId)
    suspend fun clearCategories() = selectedCategoryDAO.clearSelectedCategory()
    suspend fun addCategory(category: CategoryEntity) {
        categoryDAO.insert(category)
        selectedCategoryDAO.insert(SelectedCategoryEntity(category.id, false))
    }
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