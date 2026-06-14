package com.aitc.expensetrackerandroid.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtendedColors(
    val income: Color,
    val expense: Color,
    val warning: Color,
    val success: Color,
)

fun lightExtendedColors() = ExtendedColors(
    income = IncomeGreen,
    expense = ExpenseRed,
    warning = WarningOrange,
    success = SuccessGreen,
)

fun darkExtendedColors() = ExtendedColors(
    income = IncomeGreenDark,
    expense = ExpenseRedDark,
    warning = WarningOrangeDark,
    success = SuccessGreenDark,
)

val LocalExtendedColors = staticCompositionLocalOf { lightExtendedColors() }

val MaterialTheme.extendedColors: ExtendedColors
    @Composable
    get() = LocalExtendedColors.current
