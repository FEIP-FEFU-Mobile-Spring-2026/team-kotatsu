package ru.makoto.fefustore.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.makoto.fefustore.Entity.Const
import ru.makoto.fefustore.ui.theme.AppColors
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset

@Composable
fun CategoryPicker(
    modifier: Modifier = Modifier,
    currentCategory: MutableState<String>
) {
    val categories = Const.categories
    val selectedIndex = categories.indexOf(currentCategory.value).coerceAtLeast(0)

    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        edgePadding = 16.dp,
        containerColor = Color.White,
        contentColor = AppColors.BrownPrimary,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedIndex])
            )
        }
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { currentCategory.value = category },
                text = { Text(category) }
            )
        }
    }
}

@Composable
fun CategoryItem(
    title: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (!isActive) AppColors.BrownPrimary else AppColors.GrayLight,
            contentColor = if (!isActive) Color.White else Color.Black
        )
    ) {
        Text(title)
    }
}

@Preview
@Composable
fun CategoryPicker1() {
    val sds = remember { mutableStateOf("dddd") }
    CategoryPicker(modifier = Modifier, currentCategory = sds)
}