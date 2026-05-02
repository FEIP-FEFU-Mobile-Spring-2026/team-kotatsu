package ru.makoto.fefustore.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.makoto.fefustore.CartState
import ru.makoto.fefustore.Entity.Clothes
import ru.makoto.fefustore.R


@Composable
fun PriceButton(counter: MutableState<Int>, clothes: Clothes, clothesList: List<Clothes>) {
    Button(
        onClick = {

            counter.value++
            CartState.cart[clothesList.indexOf(clothes)] = counter.value
        },
        Modifier.height(50.dp),
        colors = ButtonColors(
            containerColor = Color(0xFFF6EFEB),
            contentColor = Color(0xFF623A29),
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.LightGray,
        ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Text(clothes.price.toString(), fontWeight = FontWeight.ExtraBold)
    }
}