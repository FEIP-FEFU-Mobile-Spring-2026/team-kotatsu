package ru.makoto.fefustore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.makoto.fefustore.Data.DTO.Category
import ru.makoto.fefustore.ui.theme.AppColors

@Composable
fun CategoryPicker(
    modifier: Modifier,
    categories: List<Category>,
    currentCategory: String,
    changeCategory: (Category) -> Unit,
    changeTag: (String) -> Unit,

    title: String,
) {

    ScrollableTabRow(
        selectedTabIndex = if (currentCategory == "") 0 else categories.indexOf(categories.find { it.id == currentCategory }) + 1,
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp),
        containerColor = AppColors.White,
        edgePadding = 2.dp,
        indicator = {},
    ) {

        Tab(
            selected = currentCategory == "",
            onClick = { changeTag("New") },
            selectedContentColor = AppColors.White,
            unselectedContentColor = AppColors.Black,
            modifier = Modifier
                .padding(horizontal = 7.dp, vertical = 20.dp)
                .clip(CircleShape)
                .background(
                    if (currentCategory == "")
                        AppColors.BrownPrimary
                    else
                        AppColors.GrayLight
                )
//                    text = {
//                CategoryItem(
//                    title = "Новинки",
//                    isActive = currentCategory == "",
//                    onClick = { changeTag("New") }
        ) {
            Text(
                text = "Новинки",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }

        /*categories.forEachIndexed { index, category ->
            val isActive =
                index + 1 == categories.indexOf(categories.find { it.id == currentCategory }) + 1
            Tab(
                selected = isActive,
                onClick = { changeCategory(category) },
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp),
                text = {
                    CategoryItem(
                        title = category.name,
                        isActive = isActive,
                        onClick = { changeCategory(category) }
                    )
                }
            )
        }*/
        categories.forEach { category ->
            val isActive = category.id == currentCategory
            Tab(
                selected = isActive,
                onClick = { changeCategory(category) },
                selectedContentColor = AppColors.White,
                unselectedContentColor = AppColors.Black,
                modifier = Modifier
                    .padding(horizontal = 7.dp, vertical = 20.dp)
                    .clip(CircleShape)
                    .background(
                        if (isActive)
                            AppColors.BrownPrimary
                        else
                            AppColors.GrayLight
                    )
            ) {
                Text(
                    text = category.name,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
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
            contentColor = if (!isActive) AppColors.White else AppColors.Black
        ),
    ) {
        Text(text = title)
    }
}

