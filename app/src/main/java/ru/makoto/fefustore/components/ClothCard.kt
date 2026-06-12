package ru.makoto.fefustore.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.StateFlow
import ru.makoto.fefustore.Data.DTO.Clothes

@Composable
fun ClothCard(
    clothes: Clothes,
    onCardClick: () -> Unit,
    cartAmount: StateFlow<Int>,
    addToCart: (String) -> Unit,
    removeFromCart: (String) -> Unit
) {
    val cartAmountState by cartAmount.collectAsState()

    Card(
        modifier = Modifier.clickable(onClick = onCardClick),
        colors = CardDefaults.cardColors(containerColor = colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .height(200.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.weight(1f),
                model = clothes.img,
                contentDescription = "Picture"
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = 5.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(clothes.title, fontWeight = FontWeight.Bold)
                    Text(clothes.description, color = Color.Gray)
                }
                CounterButton(
                    clothes = clothes,
                    cartAmount = cartAmountState,
                    addToCart = addToCart,
                    removeFromCart = removeFromCart
                )
            }
        }
    }
}