package com.aitc.expensetrackerandroid.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppDimens = staticCompositionLocalOf { appDimens(dimensScale = 1f) }

val LocalAppTextStyles = staticCompositionLocalOf {
    appTextStyles(baseTypography, lightAppColorScheme())
}

val MaterialTheme.appDimens: AppDimens
    @Composable
    get() = LocalAppDimens.current

val MaterialTheme.appTextStyles: AppTextStyles
    @Composable
    get() = LocalAppTextStyles.current
