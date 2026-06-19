package ru.makoto.fefustore.Data.Repositories

import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import ru.makoto.fefustore.Data.DAO.*
import ru.makoto.fefustore.Data.DTO.*
import ru.makoto.fefustore.Data.Entity.*
import ru.makoto.fefustore.Data.Relation.*
import ru.makoto.fefustore.Data.Remote.ApiService
import ru.makoto.fefustore.Data.Remote.ConnectivityObserver
import ru.makoto.fefustore.Data.Remote.NetworkResult
import java.io.IOException
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
    private val selectedCategoryDAO: SelectedCategoryDAO,
    private val api: ApiService,
    private val connectivityObserver: ConnectivityObserver
) {

    fun hasCachedData(): Flow<Boolean> = combine(
        clothesDAO.getClothesCount(),
        categoryDAO.getCategoriesCount(),
        clothesSizeDAO.getClothesSizeCount(),
        clothesTagDAO.getClothesTagCount(),
        tagDAO.getTagCount()
    ) { clothesCount, categoryCount, clothesSizeCount, clothesTagCount, tagCount ->
        clothesCount > 0 && categoryCount > 0 && clothesSizeCount > 0 && clothesTagCount > 0 && tagCount > 0
    }

    suspend fun fetchCatalog(): NetworkResult<String> {
        if (!connectivityObserver.isCurrentlyConnected()) {
            return NetworkResult.Error("No internet connection", 504)
        }
        return try {
            val response = api.getCatalog()
            if (response.isSuccessful) {
                NetworkResult.Success(response.body()?.string() ?: "")
            } else {
                NetworkResult.Error(response.message(), response.code())
            }
        } catch (e: IOException) {
            NetworkResult.Error(e.message ?: "Server is unreachable", 504)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun loadData(jsonData: String) {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        val productsData = json.decodeFromString<ProductsData>(jsonData)
        val tags = listOf("New", "Sale", "Popular")

        tags.withIndex().forEach { (index, tagName) ->
            addTag(TagEntity(index.toString(), tagName))
        }

        productsData.categories.forEach { cat ->
            addCategory(cat.toEntity())
        }
        productsData.products.forEach { clothes ->
            addClothes(clothes.toEntity())
            clothes.sizes.forEach { size ->
                addClothesSize(size.toEntity(clothes.id))
            }
            clothes.tags.forEach { tag ->
                setClothesTag(
                    clothes.toEntity(),
                    TagEntity(tags.indexOf(tag).toString(), tag)
                )
            }
        }
    }

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

    /* fun getCartAmount(clothesId: String): Flow<Int> = cartDAO.getItemByClothesId(clothesId).map {
         if (it.isEmpty())
             return@map 0
         return@map it[0].cart.amount
     }*/

    fun getCartAmount(clothesId: String): Flow<Int> =
        cartDAO.getItemByClothesId(clothesId).map { list ->
            list.sumOf { it.cart.amount }
        }

    /*suspend fun addItemInCart(clothesId: String) {
        val cartItem = cartDAO.getItemByClothesId(clothesId).first()
        if (cartItem.isEmpty())
            cartDAO.insert(CartEntity(clothesId = clothesId, amount = 1))
        else
            cartDAO.update(cartItem[0].cart.copy(amount = cartItem[0].cart.amount + 1))
    }*/
    suspend fun addItemInCart(clothesId: String, sizeId: String? = null) {
        val cartItems = cartDAO.getItemByClothesId(clothesId).first()
        val existingItem = cartItems.find { it.cart.sizeId == sizeId }

        if (existingItem == null) {
            cartDAO.insert(CartEntity(clothesId = clothesId, sizeId = sizeId, amount = 1))
        } else {
            cartDAO.update(existingItem.cart.copy(amount = existingItem.cart.amount + 1))
        }
    }

    /*    suspend fun removeItemFromCart(clothesId: String) {
            val cartItem = cartDAO.getItemByClothesId(clothesId).first()
            if (!cartItem.isEmpty()) {
                if (cartItem[0].cart.amount > 1)
                    cartDAO.update(cartItem[0].cart.copy(amount = cartItem[0].cart.amount - 1))
                else
                    cartDAO.delete(cartItem[0].cart)
            }
        }*/
    suspend fun removeItemFromCart(clothesId: String, sizeId: String? = null) {
        val cartItems = cartDAO.getItemByClothesId(clothesId).first()
        val existingItem = cartItems.find { it.cart.sizeId == sizeId }

        if (existingItem != null) {
            if (existingItem.cart.amount > 1) {
                cartDAO.update(existingItem.cart.copy(amount = existingItem.cart.amount - 1))
            } else {
                cartDAO.delete(existingItem.cart)
            }
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

    suspend fun clearCart() {
        cartDAO.clearCart()
    }

    suspend fun removeItemCompletelyFromCart(clothesId: String, sizeId: String? = null) {
        val cartItems = cartDAO.getItemByClothesId(clothesId).first()
        val existingItem = cartItems.find { it.cart.sizeId == sizeId }

        if (existingItem != null) {
            cartDAO.delete(existingItem.cart)
        }
    }
}