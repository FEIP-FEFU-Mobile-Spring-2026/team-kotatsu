package ru.makoto.fefustore.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.makoto.fefustore.Entity.Const
import ru.makoto.fefustore.ui.theme.AppColors
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

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
            .height(50.dp),
        containerColor = Color.White,
        indicator = {},
    ) {
        categories.forEachIndexed { index, category ->
            val isActive = index == selectedIndex

            Tab(
                selected = isActive,
                onClick = { currentCategory.value = category },
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp),
                text = {
                    CategoryItem(
                        title = category,
                        isActive = isActive,
                        onClick = { currentCategory.value = category }
                    )
                }
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
        ),
    ) {
        Text(text = title)
    }
}

@Composable
fun CategoryPickerPreview() {
    val currentCategory = remember { mutableStateOf(Const.categories.firstOrNull() ?: "") }
    CategoryPicker(currentCategory = currentCategory)
}