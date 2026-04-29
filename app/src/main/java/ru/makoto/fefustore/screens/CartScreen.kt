package ru.makoto.fefustore.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.makoto.fefustore.CartState

@Composable
fun CartScreen() {
    val cartItems = CartState.cart

    if (cartItems.isEmpty()) {
        Text(
            text = "Корзина пуста",
            modifier = Modifier.padding(16.dp)
        )
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(cartItems.keys.toList()) { itemId ->
                val count = cartItems[itemId] ?: 0
                val product = itm.find { it.id == itemId }
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