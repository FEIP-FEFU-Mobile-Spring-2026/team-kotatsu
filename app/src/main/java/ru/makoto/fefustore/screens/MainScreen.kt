package ru.makoto.fefustore.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.makoto.fefustore.R
import ru.makoto.fefustore.components.CustomBottomBar
import ru.makoto.fefustore.navigation.AppNavHost
import ru.makoto.fefustore.viewmodels.ProductsViewModel

@Composable
fun MainScreen(viewModel: ViewModel) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (viewModel is ProductsViewModel) {
        viewModel.loadData(R.raw.products, LocalContext.current.resources)
    }

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