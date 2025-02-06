package com.android.modularmvi.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val LightColorScheme = lightColorScheme(
    primary = Light.primary,
    onPrimary = Light.onPrimary,
    primaryContainer = Light.primaryContainer,
    onPrimaryContainer = Light.onPrimaryContainer,
    secondary = Light.secondary,
    onSecondary = Light.onSecondary,
    secondaryContainer = Light.secondaryContainer,
    onSecondaryContainer = Light.onSecondaryContainer,
    tertiary = Light.tertiary,
    onTertiary = Light.onTertiary,
    tertiaryContainer = Light.tertiaryContainer,
    onTertiaryContainer = Light.onTertiaryContainer,
    error = Light.error,
    errorContainer = Light.errorContainer,
    onError = Light.onError,
    onErrorContainer = Light.onErrorContainer,
    background = Light.background,
    onBackground = Light.onBackground,
    surface = Light.surface,
    onSurface = Light.onSurface,
    surfaceVariant = Light.surfaceVariant,
    onSurfaceVariant = Light.onSurfaceVariant,
    outline = Light.outline,
    inverseOnSurface = Light.inverseOnSurface,
    inverseSurface = Light.inverseSurface,
    inversePrimary = Light.inversePrimary,
    surfaceTint = Light.surfaceTint,
    outlineVariant = Light.outlineVariant,
    scrim = Light.scrim
)

val DarkColorScheme = darkColorScheme(
    primary = Dark.primary,
    onPrimary = Dark.onPrimary,
    primaryContainer = Dark.primaryContainer,
    onPrimaryContainer = Dark.onPrimaryContainer,
    secondary = Dark.secondary,
    onSecondary = Dark.onSecondary,
    secondaryContainer = Dark.secondaryContainer,
    onSecondaryContainer = Dark.onSecondaryContainer,
    tertiary = Dark.tertiary,
    onTertiary = Dark.onTertiary,
    tertiaryContainer = Dark.tertiaryContainer,
    onTertiaryContainer = Dark.onTertiaryContainer,
    error = Dark.error,
    errorContainer = Dark.errorContainer,
    onError = Dark.onError,
    onErrorContainer = Dark.onErrorContainer,
    background = Dark.background,
    onBackground = Dark.onBackground,
    surface = Dark.surface,
    onSurface = Dark.onSurface,
    surfaceVariant = Dark.surfaceVariant,
    onSurfaceVariant = Dark.onSurfaceVariant,
    outline = Dark.outline,
    inverseOnSurface = Dark.inverseOnSurface,
    inverseSurface = Dark.inverseSurface,
    inversePrimary = Dark.inversePrimary,
    surfaceTint = Dark.surfaceTint,
    outlineVariant = Dark.outlineVariant,
    scrim = Dark.scrim
)

@Composable
fun ApplicationTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            setUpEdgeToEdge(view, isDarkTheme)
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}

/**
 * Sets up edge-to-edge for the window of this [view]. The system icon colors are set to either
 * light or dark depending on whether the [isDarkTheme] is enabled or not.
 */
private fun setUpEdgeToEdge(view: View, isDarkTheme: Boolean) {
    val window = (view.context as Activity).window
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = Color.Transparent.toArgb()
    val navigationBarColor = when {
        Build.VERSION.SDK_INT >= 29 -> Color.Transparent.toArgb()
        Build.VERSION.SDK_INT >= 26 -> Color(0xFF, 0xFF, 0xFF, 0x63).toArgb()
        // Min sdk version for this app is 24, this block is for SDK versions 24 and 25
        else -> Color(0x00, 0x00, 0x00, 0x50).toArgb()
    }
    window.navigationBarColor = navigationBarColor
    val controller = WindowCompat.getInsetsController(window, view)
    controller.isAppearanceLightStatusBars = !isDarkTheme
    controller.isAppearanceLightNavigationBars = !isDarkTheme
}
