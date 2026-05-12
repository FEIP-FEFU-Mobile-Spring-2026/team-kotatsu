package ru.makoto.fefustore.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.makoto.fefustore.components.CategoryPicker
import ru.makoto.fefustore.components.ClothCard
import ru.makoto.fefustore.viewmodels.ProductsViewModel

@Composable
fun MenuScreen(navController: NavController, viewModel: ViewModel = viewModel()) {
    val uiState by (viewModel as ProductsViewModel).uiState.collectAsState()

    Column {
        CategoryPicker(
            modifier = Modifier,
            currentCategory = uiState.currentCategory,
            categories = uiState.categories,
            changeCategory = { category -> viewModel.setCategory(category.id) },
            changeTag = { tag -> viewModel.setCurrentTag(tag) },
            title = uiState.currentTag
        )
        LazyColumn {
            items(uiState.clothes.filter { (it.category == uiState.currentCategory && uiState.currentTag == "") || it.tags.containsAll(listOf(uiState.currentTag)) }) {
                clothes ->
                ClothCard(
                    clothes = clothes,
                    navController = navController,
                    cart = uiState.cart
                )
            }
        }
    }
}