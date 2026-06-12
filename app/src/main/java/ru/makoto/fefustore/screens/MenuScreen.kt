package ru.makoto.fefustore.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ru.makoto.fefustore.Data.DTO.Clothes
import ru.makoto.fefustore.Data.DTO.Size
import ru.makoto.fefustore.components.CategoryItem
import ru.makoto.fefustore.components.CategoryPicker
import ru.makoto.fefustore.components.ClothCard
import ru.makoto.fefustore.components.ClothCardSkeleton
import ru.makoto.fefustore.components.ErrorState
import ru.makoto.fefustore.ui.theme.AppColors
import ru.makoto.fefustore.utils.PriceFormatter
import ru.makoto.fefustore.viewmodels.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
    var selectedClothes by remember { mutableStateOf<Clothes?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var activeSize by remember(selectedClothes?.id) { mutableStateOf<Size?>(null) }

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
                    }) { item ->
                        ClothCard(
                            clothes = item,
                            onCardClick = { selectedClothes = item },
                            cartAmount = viewModel.getCartAmount(item.id),
                            addToCart = { clothesId: String -> viewModel.addToCart(clothesId) },
                            removeFromCart = { clothesId: String -> viewModel.removeFromCart(clothesId) }
                        )
                    }
                }
            }
        }
    }

    if (selectedClothes != null) {
        val item = selectedClothes!!
        ModalBottomSheet(
            onDismissRequest = { selectedClothes = null },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .verticalScroll(rememberScrollState())
                ) {
                    if (item.tags.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            item.tags.forEach { tag ->
                                Box(
                                    modifier = Modifier
                                        .background(AppColors.BrownPrimary, shape = CircleShape)
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = tag,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize(),
                            model = item.img,
                            contentDescription = "Picture"
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "icon",
                            tint = AppColors.GrayLight,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = item.longDescription,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 24.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(bottom = 12.dp)
                    ) {
                        item.sizes.forEach { size ->
                            CategoryItem(
                                title = size.name.toString(),
                                isActive = (activeSize?.id ?: "") != size.id,
                                onClick = { activeSize = size }
                            )
                        }
                    }
                    Button(
                        onClick = {
                            viewModel.addToCart(item.id)
                            selectedClothes = null
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Добавить в корзину - ${PriceFormatter.format(item.price)}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}