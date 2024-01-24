package com.rodrigolmti.lunch.money.companion.uikit.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LocalCompanionSpacings = staticCompositionLocalOf<CompanionSpacings> {
    error("No LocalCompanionSpacings provided")
}

private val DarkColorScheme = darkColorScheme(
    primary = CharcoalMist,
    surface = CharcoalMist,
    secondary = OceanDepth,
    tertiary = MidnightSlate,
    background = MidnightSlate,
    onSurface = CharcoalMist,
)

object CompanionTheme {

    val spacings: CompanionSpacings
        @Composable
        get() = LocalCompanionSpacings.current

}

@Composable
fun CompanionTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DarkColorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    CompositionLocalProvider(
        LocalCompanionSpacings provides CompanionSpacings,
    ) {
        MaterialTheme(
            colorScheme = DarkColorScheme,
            content = content
        )
    }
}