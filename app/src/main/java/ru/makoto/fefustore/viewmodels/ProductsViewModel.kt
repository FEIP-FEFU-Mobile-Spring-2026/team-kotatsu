package ru.makoto.fefustore.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.makoto.fefustore.Data.DTO.*
import ru.makoto.fefustore.Data.Remote.NetworkResult
import ru.makoto.fefustore.Data.Repositories.StoreRepository
import javax.inject.Inject
import kotlin.reflect.KClass

sealed interface ExceptionUiState {
    object Loading : ExceptionUiState
    object Success : ExceptionUiState
    sealed interface Error : ExceptionUiState {
        data class BannerError(val message: String) : Error
        data class SnackbarError(val message: String, val id: Int) : Error
    }
}


@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: StoreRepository,
    @ApplicationContext val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow<ExceptionUiState>(ExceptionUiState.Success)
    val uiState: StateFlow<ExceptionUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                if (!(repository.hasCachedData().firstOrNull() ?: false)) {
                    _uiState.value = ExceptionUiState.Loading
                    fetchAndLoadData(ExceptionUiState.Error.BannerError::class)
                } else {
                    fetchAndLoadData(ExceptionUiState.Error.SnackbarError::class)
                }
            } catch (e: Exception) {
                _uiState.value = ExceptionUiState.Error.BannerError("Unknown server error")
            }
        }
    }

    private suspend fun fetchAndLoadData(errorClass: KClass<out ExceptionUiState.Error>) {
        when (val response = repository.fetchCatalog()) {
            is NetworkResult.Success<String> -> {
                repository.loadData(response.data)
                _uiState.value = ExceptionUiState.Success
            }
            is NetworkResult.Error<String> -> {
                when (errorClass) {
                    ExceptionUiState.Error.BannerError::class -> {
                        Log.d("Banner", response.message)
                        _uiState.value = ExceptionUiState.Error.BannerError(response.message)
                    }
                    ExceptionUiState.Error.SnackbarError::class -> {
                        var id = 0
                        if (_uiState.value is ExceptionUiState.Error.SnackbarError) {
                            id = (_uiState.value as ExceptionUiState.Error.SnackbarError).id + 1
                        }
                        _uiState.value = ExceptionUiState.Error.SnackbarError(response.message, id)
                        Log.d("Snackbar", response.message)
                    }
                }
            }
            is NetworkResult.Loading<String> -> {
                _uiState.value = ExceptionUiState.Loading
            }
        }

    }

    fun fetchAndLoadDataSync(errorClass: KClass<out ExceptionUiState.Error>) = viewModelScope.launch {
        if (errorClass != ExceptionUiState.Error.SnackbarError::class) {
            _uiState.value = ExceptionUiState.Loading
        }
        fetchAndLoadData(errorClass)
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

    fun addToCart(clothesId: String) = viewModelScope.launch { repository.addItemInCart(clothesId) }

    fun removeFromCart(clothesId: String) = viewModelScope.launch { repository.removeItemFromCart(clothesId) }

    fun getCartAmount(clothesId: String): StateFlow<Int> = repository.getCartAmount(clothesId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    val clothesInCart: StateFlow<List<CartItem>> = repository.getAllClothesInCart().distinctUntilChanged().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun setCategory(categoryId: String?) = viewModelScope.launch {
        categoryId?.let {
            repository.selectCategory(categoryId = categoryId)
        } ?: run {
            repository.clearCategories()
        }
    }

    val currentCategory: StateFlow<String?> = repository.getSelectedCategory().distinctUntilChanged().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )
}