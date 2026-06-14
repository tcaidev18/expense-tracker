package com.aitc.expensetrackerandroid.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

fun lightAppColorScheme() = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = BrandOnPrimary,
    primaryContainer = BrandPrimaryContainer,
    onPrimaryContainer = BrandOnPrimaryContainer,
    secondary = BrandSecondary,
    onSecondary = BrandOnSecondary,
    secondaryContainer = BrandSecondaryContainer,
    onSecondaryContainer = BrandOnSecondaryContainer,
    tertiary = BrandTertiary,
    onTertiary = BrandOnTertiary,
    tertiaryContainer = BrandTertiaryContainer,
    onTertiaryContainer = BrandOnTertiaryContainer,
    background = Gray99,
    onBackground = Gray10,
    surface = Gray99,
    onSurface = Gray10,
    surfaceVariant = Gray95,
    onSurfaceVariant = Gray20,
    error = ErrorRed,
    onError = BrandOnPrimary,
)

fun darkAppColorScheme() = darkColorScheme(
    primary = BrandPrimaryDark,
    onPrimary = BrandOnPrimaryDark,
    primaryContainer = BrandPrimaryContainerDark,
    onPrimaryContainer = BrandOnPrimaryContainerDark,
    secondary = BrandSecondaryDark,
    onSecondary = BrandOnSecondaryDark,
    secondaryContainer = BrandSecondaryContainerDark,
    onSecondaryContainer = BrandOnSecondaryContainerDark,
    tertiary = BrandTertiaryDark,
    onTertiary = BrandOnTertiaryDark,
    tertiaryContainer = BrandTertiaryContainerDark,
    onTertiaryContainer = BrandOnTertiaryContainerDark,
    background = Gray10,
    onBackground = Gray95,
    surface = Gray10,
    onSurface = Gray95,
    surfaceVariant = Gray20,
    onSurfaceVariant = Gray90,
    error = ErrorRed,
    onError = BrandOnPrimary,
)
