package com.aitc.expensetrackerandroid.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class AppTextStyles(
    val screenTitle: TextStyle,
    val sectionHeader: TextStyle,
    val amountHero: TextStyle,
    val amountStandard: TextStyle,
    val listItemTitle: TextStyle,
    val listItemSubtitle: TextStyle,
    val caption: TextStyle,
    val button: TextStyle,
    val welcomeTitle: TextStyle,
    val welcomeBody: TextStyle,
    val welcomeSkip: TextStyle,
    val splashBrand: TextStyle,
    val errorMessage: TextStyle,
    val emptyMessage: TextStyle,
)

fun appTextStyles(
    typography: Typography,
    colorScheme: ColorScheme,
): AppTextStyles {
    val textColor = colorScheme.onSurface

    fun TextStyle.onThemeSurface(): TextStyle = copy(color = textColor)

    return AppTextStyles(
        screenTitle = typography.headlineLarge.onThemeSurface(),
        sectionHeader = typography.titleLarge.onThemeSurface(),
        amountHero = typography.displaySmall.copy(fontWeight = FontWeight.Bold).onThemeSurface(),
        amountStandard = typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            .onThemeSurface(),
        listItemTitle = typography.titleMedium.onThemeSurface(),
        listItemSubtitle = typography.bodyMedium.onThemeSurface(),
        caption = typography.bodySmall.onThemeSurface(),
        button = typography.labelLarge,
        welcomeTitle = typography.bodyMedium.copy(fontWeight = FontWeight.Bold).onThemeSurface()
            .copy(fontSize = 30.sp),
        welcomeBody = typography.bodyMedium.onThemeSurface(),
        welcomeSkip = typography.labelLarge.copy(fontWeight = FontWeight.Bold).onThemeSurface(),
        splashBrand = typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            .onThemeSurface(),
        errorMessage = typography.bodyLarge.onThemeSurface(),
        emptyMessage = typography.bodyLarge.onThemeSurface(),
    )
}
