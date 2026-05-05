package ru.makoto.fefustore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.makoto.fefustore.screens.MainScreen
import ru.makoto.fefustore.ui.theme.FEFUStoreTheme
import ru.makoto.fefustore.viewmodels.ProductsViewModel

class MainActivity : ComponentActivity() {
    val viewModel: ProductsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FEFUStoreTheme {
                MainScreen(viewModel)
            }
        }
    }
}
