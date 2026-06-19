package ru.makoto.fefustore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.makoto.fefustore.Data.DTO.CartItem
import ru.makoto.fefustore.ui.theme.AppColors
import ru.makoto.fefustore.utils.PriceFormatter

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    onDeleteCompletely: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            model = cartItem.clothes.img,
            contentDescription = cartItem.clothes.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 80.dp, height = 100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(AppColors.GrayBackground)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = cartItem.clothes.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Удалить",
                    tint = Color.LightGray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onDeleteCompletely() }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = cartItem.selectedSize?.name?.toString() ?: "Размер не указан",
                color = AppColors.TextGray,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = PriceFormatter.format(
                        cartItem.clothes.price *
                                cartItem.amount
                    ),
                    fontWeight = FontWeight.Bold,
                    color = AppColors.BrownSecondary,
                    fontSize = 15.sp
                )

                CounterButton(
                    amount = cartItem.amount,
                    onAdd = onAdd,
                    onRemove = onRemove,
                    modifier = Modifier.height(36.dp)
                )
            }
        }
    }
}