package ru.makoto.fefustore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.makoto.fefustore.Data.DTO.Clothes
import ru.makoto.fefustore.R
import ru.makoto.fefustore.ui.theme.AppColors

@Composable
fun CounterButton(clothes: Clothes, cartState: Map<String, Int>, addToCart: (String) -> Unit, removeFromCart: (String) -> Unit) {

    if ((cartState[clothes.id] ?: 0) == 0) {
        PriceButton(clothes) {
            addToCart(clothes.id)
        }
    } else {
        Row(
            modifier = Modifier
                .height(50.dp)
                .background(
                    AppColors.GrayLight,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp)
                    .clickable {
                        removeFromCart(clothes.id)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.remove_24px),
                    contentDescription = "Убрать",
                )
            }

            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = cartState[clothes.id].toString()
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp)
                    .clickable {
                        addToCart(clothes.id)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Добавить"
                )
            }
        }
    }
}