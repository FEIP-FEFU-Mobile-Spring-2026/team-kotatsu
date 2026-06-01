package ru.makoto.fefustore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.makoto.fefustore.screens.MainScreen
import ru.makoto.fefustore.ui.theme.FEFUStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FEFUStoreTheme {
                MainScreen()
            }
        }
    }
}