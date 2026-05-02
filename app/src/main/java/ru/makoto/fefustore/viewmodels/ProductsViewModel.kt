package ru.makoto.fefustore.viewmodels

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.makoto.fefustore.Entity.Category
import ru.makoto.fefustore.Entity.Clothes
import ru.makoto.fefustore.Entity.ProductsData
import kotlinx.serialization.json.Json


class ProductsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    data class UiState(
        val currentCategory: String = "",
        val categories : List<Category> = listOf(),
        val clothes: List<Clothes> = listOf()
        )

    fun setCategory(category: String) {
        _uiState.update{ it -> it.copy(currentCategory = category) }
    }

    fun setCategories(categories: List<Category>) {
        _uiState.update{ it -> it.copy(categories = categories) }
    }

    fun setClothes(clothes: List<Clothes>) {
        _uiState.update{ it -> it.copy(clothes = clothes) }
    }

    fun loadData(jsonRawFile : Int, resources: Resources) {
        val content: String = resources.openRawResource(jsonRawFile)
            .bufferedReader(Charsets.UTF_8).use { it.readText() }
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        _uiState.update{ it -> it.copy(categories = json.decodeFromString<ProductsData>(content).categories) }
        _uiState.update{ it -> it.copy(clothes = json.decodeFromString<ProductsData>(content).products) }
    }
}