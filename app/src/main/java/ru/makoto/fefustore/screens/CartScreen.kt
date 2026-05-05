package ru.makoto.fefustore.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import ru.makoto.fefustore.viewmodels.ProductsViewModel

@Composable
fun CartScreen(viewModel: ViewModel) {
    val uiState by (viewModel as ProductsViewModel).uiState.collectAsState()
    val cardItems by uiState.cart.cartItems.collectAsState()

    if (cardItems.isEmpty()) {
        Text(
            text = "Корзина пуста",
            modifier = Modifier.padding(16.dp)
        )
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(cardItems.keys.toList()) { itemId ->
                val count = cardItems[itemId] ?: 0
                val product = uiState.clothes.find { it.id == itemId }
                Text(
                    text = if (product != null) {
                        "${product.title} — $count шт."
                    } else {
                        "Товар удалён — $count шт."
                    },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}