package com.aitc.expensetrackerandroid.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppDimens(
    val spacingXs: Dp,
    val spacingSm: Dp,
    val spacingMd: Dp,
    val spacingLg: Dp,
    val spacingXl: Dp,
    val iconSmall: Dp,
    val iconMedium: Dp,
    val iconLarge: Dp,
    val listItemMinHeight: Dp,
    val screenPadding: Dp,
)

fun appDimens(dimensScale: Float): AppDimens = AppDimens(
    spacingXs = (Spacing.extraSmall.value * dimensScale).dp,
    spacingSm = (Spacing.small.value * dimensScale).dp,
    spacingMd = (Spacing.medium.value * dimensScale).dp,
    spacingLg = (Spacing.large.value * dimensScale).dp,
    spacingXl = (Spacing.extraLarge.value * dimensScale).dp,
    iconSmall = (20f * dimensScale).dp,
    iconMedium = (24f * dimensScale).dp,
    iconLarge = (32f * dimensScale).dp,
    listItemMinHeight = (56f * dimensScale).dp,
    screenPadding = (Spacing.medium.value * dimensScale).dp,
)
