package com.diego.matesanz.arcaneum.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Equator,
    onPrimary = SealBrown,
    primaryContainer = Equator,
    onPrimaryContainer = SealBrown,
    secondary = CoffeeBean,
    onSecondary = Janna,
    secondaryContainer = CoffeeBean,
    onSecondaryContainer = Janna,
    tertiary = Janna,
    onTertiary = CoffeeBean,
    tertiaryContainer = Janna,
    onTertiaryContainer = CoffeeBean,
)

private val LightColorScheme = lightColorScheme(
    primary = Equator,
    onPrimary = SealBrown,
    primaryContainer = Equator,
    onPrimaryContainer = SealBrown,
    secondary = CoffeeBean,
    onSecondary = Janna,
    secondaryContainer = CoffeeBean,
    onSecondaryContainer = Janna,
    tertiary = Janna,
    onTertiary = CoffeeBean,
    tertiaryContainer = Janna,
    onTertiaryContainer = CoffeeBean,
    surfaceVariant = Merino,
    onSurfaceVariant = CoffeeBean,
)

@Composable
fun ArcaneumTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        //darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
