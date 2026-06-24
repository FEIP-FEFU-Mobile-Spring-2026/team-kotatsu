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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.makoto.fefustore.data.dto.CartItem
import ru.makoto.fefustore.data.dto.Category
import ru.makoto.fefustore.data.dto.Clothes
import ru.makoto.fefustore.data.dto.Size
import ru.makoto.fefustore.data.remote.NetworkResult
import ru.makoto.fefustore.data.repositories.StoreRepository
import javax.inject.Inject
import kotlin.reflect.KClass

sealed interface ExceptionUiState {
    object Loading : ExceptionUiState

    object Success : ExceptionUiState

    sealed interface Error : ExceptionUiState {
        data class BannerError(
            val message: String,
        ) : Error

        data class SnackbarError(
            val message: String,
            val id: Int,
        ) : Error
    }
}

@HiltViewModel
class ProductsViewModel
    @Inject
    constructor(
        private val repository: StoreRepository,
        @ApplicationContext val context: Context,
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
                            _uiState.value = ExceptionUiState.Error.BannerError(response.message)
                        }

                        ExceptionUiState.Error.SnackbarError::class -> {
                            var id = 0
                            if (_uiState.value is ExceptionUiState.Error.SnackbarError) {
                                id = (_uiState.value as ExceptionUiState.Error.SnackbarError).id + 1
                            }
                            _uiState.value = ExceptionUiState.Error.SnackbarError(response.message, id)
                        }
                    }
                }

                is NetworkResult.Loading<String> -> {
                    _uiState.value = ExceptionUiState.Loading
                }
            }
        }

        fun fetchAndLoadDataSync(errorClass: KClass<out ExceptionUiState.Error>) =
            viewModelScope.launch {
                if (errorClass != ExceptionUiState.Error.SnackbarError::class) {
                    _uiState.value = ExceptionUiState.Loading
                }
                fetchAndLoadData(errorClass)
            }

        val clothes: StateFlow<List<Clothes>> =
            repository.getAllClothes().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList(),
            )

        val categories: StateFlow<List<Category>> =
            repository.getAllCategories().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList(),
            )

        fun addToCart(
            clothesId: String,
            size: Size? = null,
            isQuickAdd: Boolean = false,
        ) = viewModelScope.launch {
            if (isQuickAdd) {
                val cartItems = clothesInCart.value
                val existingItem = cartItems.find { it.clothes.id == clothesId }

                if (existingItem != null) {
                    repository.addItemInCart(clothesId, existingItem.selectedSize?.id)
                    return@launch
                }
            }

            repository.addItemInCart(clothesId, size?.id)
        }

        fun removeFromCart(
            clothesId: String,
            size: Size? = null,
            isQuickAdd: Boolean = false,
        ) = viewModelScope.launch {
            if (isQuickAdd) {
                val cartItems = clothesInCart.value
                val existingItem = cartItems.find { it.clothes.id == clothesId }

                if (existingItem != null) {
                    repository.removeItemFromCart(clothesId, existingItem.selectedSize?.id)
                    return@launch
                }
            }

            repository.removeItemFromCart(clothesId, size?.id)
        }

        fun getCartAmount(clothesId: String): StateFlow<Int> =
            repository.getCartAmount(clothesId).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0,
            )

        val clothesInCart: StateFlow<List<CartItem>> =
            repository.getAllClothesInCart().distinctUntilChanged().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList(),
            )

        fun setCategory(categoryId: String?) =
            viewModelScope.launch {
                categoryId?.let {
                    repository.selectCategory(categoryId = categoryId)
                } ?: run {
                    repository.clearCategories()
                }
            }

        val currentCategory: StateFlow<String?> =
            repository.getSelectedCategory().distinctUntilChanged().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null,
            )

        val cartTotalPrice: StateFlow<Int> =
            clothesInCart
                .map { items ->
                    items.sumOf { it.clothes.price * it.amount }
                }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

        fun clearCart() = viewModelScope.launch { repository.clearCart() }

        fun removeCompletelyFromCart(
            clothesId: String,
            sizeId: String?,
        ) = viewModelScope.launch {
            repository.removeItemCompletelyFromCart(clothesId, sizeId)
        }

        fun checkoutOrder() =
            viewModelScope.launch {
                repository.clearCart()
            }
    }
