package ru.makoto.fefustore.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.makoto.fefustore.screens.CardScreen
import ru.makoto.fefustore.screens.CartScreen
import ru.makoto.fefustore.screens.MenuScreen
import ru.makoto.fefustore.viewmodels.ProductsViewModel

sealed class Destination(
    var route: String, val label: String,
    val icon: ImageVector
) {
    data object MENU : Destination("menu", "Меню", Icons.Outlined.Home)
    data object CART : Destination("cart", "Корзина", Icons.Outlined.ShoppingCart)

    class CARD(id: String) : Destination("card/$id", "Карточка", Icons.Outlined.ShoppingCart)
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ProductsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Destination.MENU.route,
        modifier = modifier
    ) {
        composable(Destination.MENU.route) { MenuScreen(navController, viewModel,) }
        composable(Destination.CART.route) { CartScreen(viewModel) }

        composable(
            Destination.CARD("{id}").route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            val cardId = it.arguments?.getString("id")!!
            CardScreen(cardId, navController, viewModel)
        }
    }
}