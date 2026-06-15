package com.aitc.expensetrackerandroid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun ExpenseTrackerAndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    windowSizeClass: WindowSizeClass,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) darkAppColorScheme() else lightAppColorScheme()
    val extendedColors = if (darkTheme) darkExtendedColors() else lightExtendedColors()
    val dimenScale = dimenScaleFor(windowSizeClass.widthSizeClass)
    val typography = scaledTypography(dimenScale.textScale)
    val appDimens = appDimens(dimenScale.dimensScale)
    val appTextStyles = appTextStyles(typography)

    CompositionLocalProvider(
        LocalExtendedColors provides extendedColors,
        LocalAppDimens provides appDimens,
        LocalAppTextStyles provides appTextStyles,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = AppShapes,
            content = content,
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ExpenseTrackerAndroidThemePreview(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val windowInfo = LocalWindowInfo.current
    val height = windowInfo.containerSize.height
    val width = windowInfo.containerSize.width
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(width.dp, height.dp),
    )
    ExpenseTrackerAndroidTheme(
        darkTheme = darkTheme,
        windowSizeClass = windowSizeClass,
        content = content,
    )
}
