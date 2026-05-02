package ru.makoto.fefustore.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import ru.makoto.fefustore.components.CategoryPicker
import ru.makoto.fefustore.components.ClothCard
import ru.makoto.fefustore.viewmodels.ProductsViewModel
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MenuScreen(navController: NavController, viewModel: ViewModel = viewModel()) {
    val uiState by (viewModel as ProductsViewModel).uiState.collectAsState()

    Column {
        CategoryPicker(modifier = Modifier, currentCategory = uiState.currentCategory, categories = uiState.categories) {
            viewModel.setCategory(it.id)
        }
        LazyColumn {
            items(uiState.clothes.filter { it.category == uiState.currentCategory }) { clothes ->
                ClothCard(
                    clothes = clothes,
                    navController = navController,
                    clothesList = uiState.clothes
                )
            }
        }
    }
}