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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.makoto.fefustore.Entity.Clothes
import ru.makoto.fefustore.R
import kotlin.math.max

@Composable
fun CounterButton(clothes: Clothes) {
    val counter = remember { mutableStateOf(0) }
    if (counter.value == 0) PriceButton(counter, clothes) else {
        Row(
            modifier = Modifier
                .height(50.dp)
                .background(
                    Color(0xFFE8E8E8),
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp)
                    .clickable(onClick = {
                        if (max(0, counter.value--) == 0) {
                            //counter.value = 0
                        }
                    }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.remove_24px),
                    contentDescription = "Убрать",
                )
            }

            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = counter.value.toString()
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp)
                    .clickable(onClick = {
                        counter.value++
                    }),
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