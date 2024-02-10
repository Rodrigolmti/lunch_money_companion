package com.rodrigolmti.lunch.money.companion.uikit.theme

import android.app.Activity
import androidx.compose.foundation.LocalIndication
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LocalCompanionTypography = staticCompositionLocalOf<CompanionTypography> {
    error("No LocalCompanionTypography provided")
}

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
    onPrimary = FadingGrey,
    onTertiary = FadingGrey,
)

object CompanionTheme {

    val spacings: CompanionSpacings
        @Composable
        get() = LocalCompanionSpacings.current

    val typography: CompanionTypography
        @Composable
        get() = LocalCompanionTypography.current
}

@Composable
fun CompanionTheme(content: @Composable () -> Unit) {
    val ripple = rememberRipple(bounded = false)
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
        LocalCompanionTypography provides CompanionTypography,
        LocalIndication provides ripple,
    ) {
        MaterialTheme(
            colorScheme = DarkColorScheme,
            content = content
        )
    }
}