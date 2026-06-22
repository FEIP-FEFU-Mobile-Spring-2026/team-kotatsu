package ru.makoto.fefustore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.makoto.fefustore.components.CartItemCard
import ru.makoto.fefustore.components.CartTextField
import ru.makoto.fefustore.ui.theme.AppColors
import ru.makoto.fefustore.utils.PriceFormatter
import ru.makoto.fefustore.viewmodels.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: ProductsViewModel,
) {
    val clothesInCart by viewModel.clothesInCart.collectAsState()
    val totalPrice by viewModel.cartTotalPrice.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }

    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]+$".toRegex()
    val isFormValid = name.isNotBlank() && email.matches(emailRegex)

    var showSuccessSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var showClearDialog by remember { mutableStateOf(false) }

    if (showSuccessSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showSuccessSheet = false
                navController.popBackStack()
            },
            sheetState = sheetState,
            containerColor = Color.White,
        ) {
            SuccessOrder(
                onReturnHomeClick = {
                    showSuccessSheet = false
                    navController.popBackStack()
                },
            )
        }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("Очистить корзину?") },
            text = { Text("Вы уверены, что хотите удалить все товары из корзины?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.clearCart()
                        showClearDialog = false
                    },
                ) {
                    Text("Да", color = AppColors.BrownPrimary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("Отмена", color = AppColors.BrownPrimary)
                }
            },
        )
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Корзина", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    if (clothesInCart.isNotEmpty()) {
                        IconButton(onClick = { showClearDialog = true }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Очистить",
                                tint = Color.LightGray,
                            )
                        }
                    }
                },
                windowInsets = WindowInsets(0, 0, 0, 0),
                colors =
                    TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.White,
                    ),
            )
        },
        bottomBar = {
            if (clothesInCart.isNotEmpty()) {
                Surface(
                    color = Color.White,
                    shadowElevation = 8.dp,
                ) {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 20.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("Итого", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(
                                text = PriceFormatter.format(totalPrice),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                viewModel.checkoutOrder()
                                showSuccessSheet = true
                            },
                            enabled = isFormValid,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor = AppColors.BrownSecondary,
                                    disabledContainerColor = AppColors.BrownDisabled,
                                    contentColor = Color.White,
                                    disabledContentColor = Color.White,
                                ),
                        ) {
                            Text("Оформить", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        },
    ) { paddingValues ->
        if (clothesInCart.isEmpty()) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) {
                Text("Корзина пуста", color = AppColors.TextGray, fontSize = 16.sp)
            }
        } else {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
            ) {
                items(clothesInCart) { cartItem ->
                    CartItemCard(
                        cartItem = cartItem,
                        onAdd = { viewModel.addToCart(cartItem.clothes.id, cartItem.selectedSize) },
                        onRemove = {
                            viewModel.removeFromCart(
                                cartItem.clothes.id,
                                cartItem.selectedSize,
                            )
                        },
                        onDeleteCompletely = {
                            viewModel.removeCompletelyFromCart(
                                cartItem.clothes.id,
                                cartItem.selectedSize?.id,
                            )
                        },
                    )
                    HorizontalDivider(color = AppColors.GrayBackground, thickness = 1.dp)
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))

                    CartTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = "Имя*",
                        singleLine = true,
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CartTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Почта*",
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CartTextField(
                        value = comment,
                        onValueChange = { comment = it },
                        placeholder = "Комментарий к заказу",
                        singleLine = false,
                        modifier = Modifier.height(100.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun SuccessOrder(onReturnHomeClick: () -> Unit) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Outlined.CheckCircle,
            contentDescription = "Success",
            modifier = Modifier.size(80.dp),
            tint = AppColors.BrownSecondary,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Заказ успешно оформлен",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Подтверждение и чек отправили на\nвашу почту",
            color = AppColors.TextGray,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onReturnHomeClick,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.BrownSecondary),
        ) {
            Text("Вернуться на главную", fontSize = 16.sp, color = Color.White)
        }
    }
}
