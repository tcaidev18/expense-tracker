package com.aitc.expensetrackerandroid.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

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
)

fun appTextStyles(typography: Typography): AppTextStyles = AppTextStyles(
    screenTitle = typography.headlineLarge,
    sectionHeader = typography.titleLarge,
    amountHero = typography.displaySmall.copy(fontWeight = FontWeight.Bold),
    amountStandard = typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
    listItemTitle = typography.titleMedium,
    listItemSubtitle = typography.bodyMedium,
    caption = typography.bodySmall,
    button = typography.labelLarge,
)
