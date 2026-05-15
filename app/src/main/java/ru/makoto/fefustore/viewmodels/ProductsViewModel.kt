package ru.makoto.fefustore.viewmodels

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.makoto.fefustore.Data.DTO.*
import kotlinx.serialization.json.Json
import ru.makoto.fefustore.Data.Cart
import ru.makoto.fefustore.Data.Entity.TagEntity
import ru.makoto.fefustore.Data.Entity.toClothes
import ru.makoto.fefustore.Data.Repositories.StoreRepository
import ru.makoto.fefustore.R
import javax.inject.Inject

// Та самая инъекция зависимости репозитория
@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: StoreRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            // парсим файлик
            val content: String = context.resources.openRawResource(R.raw.products)
                .bufferedReader(Charsets.UTF_8).use { it.readText() }
            val json = Json {
                ignoreUnknownKeys = true
                isLenient = true
            }
            val productsData = json.decodeFromString<ProductsData>(content)
            val tags = listOf("New", "Sale", "Popular")
            // инициализация в локальную бд
            tags.withIndex().forEach { (index, tagName) ->
                repository.addTag(TagEntity(index.toString(), tagName))
            }

            productsData.categories.forEach { cat ->
                repository.addCategory(cat.toEntity())
            }
            productsData.products.forEach { clothes ->
                repository.addClothes(clothes.toEntity())
                clothes.sizes?.forEach { size ->
                    repository.addClothesSize(size.toEntity(clothes.id))
                }
                clothes.tags?.forEach { tag ->
                    repository.setClothesTag(
                        clothes.toEntity(),
                        TagEntity(tags.indexOf(tag).toString(), tag)
                    )
                }
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
        val currentTag: String = "New",
        val cart: Cart = Cart
    )
    fun setCategory(category: String) {
        _uiState.update{ it -> it.copy(currentCategory = category) }
        _uiState.update{ it -> it.copy(currentTag = "") }
    }

    fun setCurrentTag(tag: String) {
        _uiState.update{ it -> it.copy(currentTag = tag) }
        _uiState.update{ it -> it.copy(currentCategory = "") }
    }

    // Мы не добавляем что то прямо в бд помимо json файла. Поэтому это можно убрать
//    fun setCategories(categories: List<Category>) {
//        _uiState.update{ it -> it.copy(categories = categories) }
//    }
//
//    fun setClothes(clothes: List<Clothes>) {
//        _uiState.update{ it -> it.copy(clothes = clothes) }
//    }


}