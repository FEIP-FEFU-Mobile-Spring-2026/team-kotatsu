package ru.makoto.fefustore.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.makoto.fefustore.components.CategoryPicker
import ru.makoto.fefustore.components.ClothCard
import ru.makoto.fefustore.components.ClothCardSkeleton
import ru.makoto.fefustore.components.ErrorState
import ru.makoto.fefustore.viewmodels.ProductsViewModel

@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: ProductsViewModel,
) {
    val clothes by viewModel.clothes.collectAsState()
    val categories by viewModel.categories.collectAsState()

    val currentCategory by viewModel.currentCategory.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column {
        CategoryPicker(
            modifier = Modifier,
            currentCategory = currentCategory,
            categories = categories,
            changeCategory = { category -> viewModel.setCategory(category?.id) },
        )

        when {
            isLoading -> {
                LazyColumn {
                    items(4) {
                        ClothCardSkeleton()
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
            errorMessage != null -> {
                ErrorState(
                    message = errorMessage ?: "Неизвестная ошибка",
                    onRetry = { viewModel.fetchData() }
                )
            }
            else -> {
                LazyColumn {
                    items(clothes.filter {
                        (currentCategory == null && it.tags.contains("New")) || (it.category == currentCategory)
                    }) {

                        ClothCard(
                            clothes = it,
                            navController = navController,
                            // можно потом предзагружать
                            cartAmount = viewModel.getCartAmount(it.id),
                            addToCart = { clothesId: String -> viewModel.addToCart(clothesId) },
                            removeFromCart = { clothesId: String -> viewModel.removeFromCart(clothesId) }
                        )
                    }
                }
            }
        }
    }
}