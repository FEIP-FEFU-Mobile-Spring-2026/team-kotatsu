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
                    clothes = clothes,
                    navController = navController
                )
            }
        }
    }
}

val itm = listOf(
    Clothes(
        0, "Новинки", R.drawable.shapka,
        "Шапка-ушанка",
        "Теплая шапка из села Молочное",
        1000,
        Size.entries,
        "Теплая шапка из села Молочное, где живут пауки-людоеды и есть трамвай."
    ),
    Clothes(
        1, "Новинки", R.drawable.trash,
        "Костюм-сауна",
        "Костюм-сауна для похудения",
        2000,
        listOf(Size.L, Size.XXL, Size.XL, Size.S),
        "Оригинальная одежда от французских дизайнеров, которая вам точно понравится."
    ),
    Clothes(
        2, "Джинсы", R.drawable.jeans,
        "Джинсы",
        "Женские джинсы",
        100,
        listOf(Size.L, Size.XXL),
        "Женские рваные джинсы High Street, винтажные мешковатые джинсы, уличная одежда Y2K."
    ),
    Clothes(
        3, "Шапки", R.drawable.shapka,
        "Шапка-ушанка",
        "Теплая шапка из села Молочное",
        500,
        listOf(Size.L, Size.XXL, Size.XL),
        "Теплая шапка из села Молочное, где живут пауки-людоеды и есть трамвай."
    ),
    Clothes(
        4, "Джинсы", R.drawable.jeans,
        "Джинсы",
        "Женские джинсы",
        10000,
        listOf(Size.L, Size.XXL, Size.XL, Size.S, Size.M),
        "Женские рваные джинсы High Street, винтажные мешковатые джинсы, уличная одежда Y2K."
    ),
    Clothes(
        5, "Костюмы", R.drawable.trash,
        "Костюм",
        "Спортивный костюм",
        3000,
        listOf(Size.L, Size.XXL, Size.XL),
        "Удобный спортивный костюм для повседневной носки."
    ),
    Clothes(
        14, "Костюмы", R.drawable.jeans,
        "Деловой костюм",
        "Классический костюм-двойка",
        15000,
        listOf(Size.S, Size.M, Size.L, Size.XL),
        "Строгий деловой костюм из итальянской шерсти. Пиджак на двух пуговицах, зауженные брюки. Премиум качество."
    ),
    Clothes(
        12, "Шапки", R.drawable.shapka,
        "Кепка",
        "Кепка с прямым козырьком",
        900,
        listOf(Size.S, Size.M, Size.L),
        "Кепка в стиле стритвир. Регулируемый размер сзади. Плотная ткань держит форму."
    ),
    Clothes(
        13, "Шапки", R.drawable.trash,
        "Панама",
        "Лёгкая летняя панама",
        700,
        listOf(Size.S, Size.M, Size.L),
        "Хлопковая панама для защиты от солнца. Складывается, помещается в карман. Идеальна для путешествий."
    ),
    Clothes(
        9, "Футболки", R.drawable.trash,
        "Футболка оверсайз",
        "Свободная футболка унисекс",
        1200,
        listOf(Size.XS, Size.S, Size.M, Size.L, Size.XL, Size.XXL),
        "Базовая футболка свободного кроя из органического хлопка. Доступна в 6 размерах. Не теряет форму после стирки."
    ),
    Clothes(
        10, "Футболки", R.drawable.shapka,
        "Футболка с принтом",
        "Футболка с авторским принтом",
        1500,
        listOf(Size.XS, Size.S, Size.M, Size.L, Size.XL),
        "Лимитированная серия футболок с принтами от локальных художников. Ручная работа, качественная шелкография."
    ),
    Clothes(
        2, "Джинсы", R.drawable.jeans,
        "Джинсы скинни",
        "Узкие женские джинсы",
        2900,
        listOf(Size.XS, Size.S, Size.M, Size.L),
        "Классические узкие джинсы, которые подчеркнут фигуру. Высокое качество денима, не растягиваются после носки."
    ),
    Clothes(
        4, "Джинсы", R.drawable.jeans,
        "Джинсы клёш",
        "Расклешённые от колена",
        4200,
        listOf(Size.XS, Size.S, Size.M, Size.L, Size.XL),
        "Винтажные джинсы клёш. Возвращение стиля 70-х! Плотный деним, высокая посадка, расклешение от колена."
    ),
    Clothes(
        0, "Новинки", R.drawable.shapka,
        "Шапка-ушанка",
        "Теплая шапка из села Молочное",
        1000,
        Size.entries,
        "Теплая шапка из села Молочное, где живут пауки-людоеды и есть трамвай. В Мурино такой шапки боялись все."
    ),
    Clothes(
        1, "Новинки", R.drawable.trash,
        "Костюм-сауна",
        "Костюм-сауна для похудения",
        2000,
        listOf(Size.L, Size.XXL, Size.XL, Size.S),
        "Оригинальная одежда от французских дизайнеров, которая вам точно понравится. Идеально для занятий спортом."
    ),

)