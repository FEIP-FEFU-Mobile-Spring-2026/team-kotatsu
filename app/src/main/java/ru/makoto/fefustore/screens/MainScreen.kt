package ru.makoto.fefustore.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.makoto.fefustore.components.CustomBottomBar
import ru.makoto.fefustore.navigation.AppNavHost
import ru.makoto.fefustore.viewmodels.ProductsViewModel

@Composable
fun MainScreen(viewModel: ProductsViewModel) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute?.startsWith("card") != true) {
                CustomBottomBar(navController = navController)
            }
        }
    ) { paddingValues ->
        AppNavHost(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(paddingValues)
        )
    }
}