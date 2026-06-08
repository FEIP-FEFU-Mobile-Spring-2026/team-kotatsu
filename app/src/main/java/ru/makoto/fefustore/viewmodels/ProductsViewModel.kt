package ru.makoto.fefustore.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.makoto.fefustore.Data.DTO.*
import kotlinx.serialization.json.Json
import ru.makoto.fefustore.Data.Cart
import ru.makoto.fefustore.Data.Entity.TagEntity
import ru.makoto.fefustore.Data.Repositories.StoreRepository
import ru.makoto.fefustore.R
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: StoreRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val content: String = context.resources.openRawResource(R.raw.products)
                    .bufferedReader(Charsets.UTF_8).use { it.readText() }
                val json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
                val productsData = json.decodeFromString<ProductsData>(content)
                val tags = listOf("New", "Sale", "Popular")

                tags.withIndex().forEach { (index, tagName) ->
                    repository.addTag(TagEntity(index.toString(), tagName))
                }

                productsData.categories.forEach { cat ->
                    repository.addCategory(cat.toEntity())
                }
                productsData.products.forEach { clothes ->
                    repository.addClothes(clothes.toEntity())
                    clothes.sizes.forEach { size ->
                        repository.addClothesSize(size.toEntity(clothes.id))
                    }
                    clothes.tags.forEach { tag ->
                        repository.setClothesTag(
                            clothes.toEntity(),
                            TagEntity(tags.indexOf(tag).toString(), tag)
                        )
                    }
                }
                _isLoading.value = false

            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = "Ошибка при загрузке данных. Попробуйте снова."
            }
        }
    }

    val clothes: StateFlow<List<Clothes>> = repository.getAllClothes().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val categories: StateFlow<List<Category>> = repository.getAllCategories().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val currentCategory: String = "",
        val currentTag: String = "New"
    )

    fun addToCart(clothesId: String) = viewModelScope.launch { repository.addItemInCart(clothesId) }

    fun removeFromCart(clothesId: String) = viewModelScope.launch { repository.removeItemFromCart(clothesId) }

    fun getCartAmount(clothesId: String): StateFlow<Int> = repository.getCartAmount(clothesId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    fun getAllClothesInCart(): StateFlow<List<CartItem>> = repository.getAllClothesInCart().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun setCategory(category: String) {
        _uiState.update{ it -> it.copy(currentCategory = category) }
        _uiState.update{ it -> it.copy(currentTag = "") }
    }

    fun setCurrentTag(tag: String) {
        _uiState.update{ it -> it.copy(currentTag = tag) }
        _uiState.update{ it -> it.copy(currentCategory = "") }
    }
}