package ru.makoto.fefustore.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFA68170),
    onPrimary = Color.White,
    secondary = Color(0xFF5D4037),
    onSecondary = Color.White,
    primaryContainer = Color(0xFFF5EEE9),
    onPrimaryContainer = Color(0xFF5D4037),
    secondaryContainer = Color(0xFFF5F5F5),
    onSecondaryContainer = Color(0xFF1A1A1A),
    tertiary = AppColors.BrownPrimary,
    background = Color(0xFFF7F7F7),
    onBackground = Color(0xFF1A1A1A),
    surface = Color.White,
    onSurface = Color(0xFF1A1A1A),
    outline = Color(0xFF8E8E8E),
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = Color.Black,
)

@Composable
fun FEFUStoreTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}