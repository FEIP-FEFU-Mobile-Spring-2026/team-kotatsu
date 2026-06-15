package ru.makoto.fefustore.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.makoto.fefustore.components.CustomBottomBar
import ru.makoto.fefustore.navigation.AppNavHost
import ru.makoto.fefustore.viewmodels.ExceptionUiState
import ru.makoto.fefustore.viewmodels.ProductsViewModel

@Composable
fun MainScreen(viewModel: ProductsViewModel) {
    val navController = rememberNavController()

    val snackbarHostState = remember { SnackbarHostState() }
    val errorState by viewModel.uiState.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val clothesInCart by viewModel.clothesInCart.collectAsState()
    val cartCount = remember(clothesInCart) {
        clothesInCart.sumOf { it.amount }
    }

    LaunchedEffect(errorState) {
        Log.d("NEW STATE", errorState.toString())
        when (errorState) {
            is ExceptionUiState.Error.SnackbarError -> {
                val errorState = errorState as ExceptionUiState.Error.SnackbarError
                val result = snackbarHostState.showSnackbar(
                    message = errorState.message,
                    actionLabel = "Повторить попытку",
                    duration = SnackbarDuration.Indefinite
                )

                if (result == SnackbarResult.ActionPerformed) {
                    viewModel.fetchAndLoadDataSync(ExceptionUiState.Error.SnackbarError::class)
                }
            }
            else -> {
                snackbarHostState.currentSnackbarData?.dismiss()
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (currentRoute?.startsWith("card") != true) {
                CustomBottomBar(
                    navController = navController,
                    cartCount = cartCount
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        AppNavHost(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(paddingValues)
        )
    }
}