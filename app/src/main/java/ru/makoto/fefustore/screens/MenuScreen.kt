package ru.makoto.fefustore.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.makoto.fefustore.components.CategoryPicker
import ru.makoto.fefustore.components.ClothCard
import ru.makoto.fefustore.viewmodels.ProductsViewModel

@Composable
fun MenuScreen(navController: NavController, viewModel: ProductsViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val clothes by viewModel.clothes.collectAsState()
    val categories by viewModel.categories.collectAsState()

    Column {
        CategoryPicker(
            modifier = Modifier,
            currentCategory = uiState.currentCategory,
            categories = categories,
            changeCategory = { category -> viewModel.setCategory(category.id) },
            changeTag = { tag -> viewModel.setCurrentTag(tag) },
            title = uiState.currentTag
        )
        LazyColumn {
            items(clothes.filter { (it.category == uiState.currentCategory && uiState.currentTag == "") || it.tags?.containsAll(listOf(uiState.currentTag)) ?: false}) {
                ClothCard(
                    clothes = it,
                    navController = navController,
                    cart = uiState.cart
                )
            }
        }
    }
}