package ru.makoto.fefustore.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.makoto.fefustore.Entity.Clothes
import ru.makoto.fefustore.Entity.Size
import ru.makoto.fefustore.R
import ru.makoto.fefustore.components.CategoryPicker
import ru.makoto.fefustore.components.ClothCard

@Composable
fun MenuScreen(navController: NavController) {
    val currentCategory = remember { mutableStateOf("Новинки") }
    Column {
        CategoryPicker(modifier = Modifier, currentCategory = currentCategory)
        LazyColumn {
            items(itm.filter { it.category == currentCategory.value }) { clothes ->
                ClothCard(
                    clothes, navController

                )
            }
        }
    }
}

val itm = listOf(
    Clothes(
        0,
        "Новинки",
        R.drawable.shapka,
        "Шапка-ушанка",
        "Теплая шапка прямо из села Молочное, где живут пауки",
        1000,
        Size.entries,
        "Теплая шапка прямо из села Молочное, где живут пауки-людоеды и есть трамвай. В Мурино такой шапки боялись все, будут ли в молочном?"
    ),
    Clothes(
        1,
        "Новинки",
        R.drawable.trash,
        "Костюм-сауна",
        "Костюм-сауна для похудения",
        2000,
        listOf(Size.L, Size.XXL, Size.XL, Size.S),
        "Оригинальная одежда от французских дизайнеров, которая вам точно понравится."
    ),
    Clothes(
        2,
        "Джинсы",
        R.drawable.jeans,
        "Джинсы",
        "Женские джинсы",
        100,
        listOf(Size.L, Size.XXL,),
        "Женские рваные джинсы High Street, винтажные мешковатые джинсы, прямые брюки, узкие повседневные ковбойские джинсовые брюки для женщин, уличная одежда Y2K, широкие джинсы для девочек"
    ),
    Clothes(
        3,
        "Новинки",
        R.drawable.shapka,
        "Шапка-ушанка",
        "Теплая шапка прямо из села Молочное, где живут пауки и есть трамвай",
        500,
        listOf(Size.L, Size.XXL, Size.XL),
        "Теплая шапка прямо из села Молочное, где живут пауки-людоеды и есть трамвай. В Мурино такой шапки боялись все, будут ли в Молочном?"
    ),
    Clothes(
        4,
        "Джинсы",
        R.drawable.jeans,
        "Джинсы",
        "Женские джинсы",
        10000,
        listOf(Size.L, Size.XXL, Size.XL, Size.S, Size.M),
        "Женские рваные джинсы High Street, винтажные мешковатые джинсы, прямые брюки, узкие повседневные ковбойские джинсовые брюки для женщин, уличная одежда Y2K, широкие джинсы для девочек"
    ),
    Clothes(
        5,
        "Новинки",
        R.drawable.shapka,
        "frfrfrf",
        "frfrfrfr",
        100,
        listOf(Size.L, Size.XXL, Size.XL),
        "dkdkdkdkdkdkdkdkdkdk"
    ),
)