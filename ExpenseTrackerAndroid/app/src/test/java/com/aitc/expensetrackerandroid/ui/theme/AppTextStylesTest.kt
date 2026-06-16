package com.aitc.expensetrackerandroid.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AppTextStylesTest {

    @Test
    fun appTextStyles_derivesFromTypography_withOnSurfaceColor() {
        val typography = baseTypography
        val colorScheme = lightAppColorScheme()
        val styles = appTextStyles(typography, colorScheme)
        val textColor = colorScheme.onSurface

        assertEquals(
            typography.headlineLarge.copy(color = textColor),
            styles.screenTitle,
        )
        assertEquals(
            typography.titleLarge.copy(color = textColor),
            styles.sectionHeader,
        )
        assertEquals(
            typography.titleMedium.copy(color = textColor),
            styles.listItemTitle,
        )
        assertEquals(
            typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = textColor,
            ),
            styles.welcomeTitle,
        )
        assertEquals(
            typography.bodyMedium.copy(color = textColor),
            styles.welcomeBody,
        )
        assertEquals(
            typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = textColor,
            ),
            styles.splashBrand,
        )
        assertEquals(
            typography.bodyLarge.copy(color = textColor),
            styles.errorMessage,
        )
        assertEquals(
            typography.bodyLarge.copy(color = textColor),
            styles.emptyMessage,
        )
        assertEquals(typography.labelLarge, styles.button)
        assertEquals(Color.Unspecified, styles.button.color)
    }

    @Test
    fun appTextStyles_usesDarkOnSurfaceInDarkScheme() {
        val colorScheme = darkAppColorScheme()
        val styles = appTextStyles(baseTypography, colorScheme)

        assertEquals(colorScheme.onSurface, styles.welcomeTitle.color)
    }

    @Test
    fun baseTypography_usesAppFontFamily() {
        assertEquals(AppFontFamily, baseTypography.bodyLarge.fontFamily)
    }

    @Test
    fun scaledTypography_increasesFontSize() {
        val baseSize = baseTypography.headlineLarge.fontSize
        val scaledSize = scaledTypography(1.15f).headlineLarge.fontSize

        assertTrue(scaledSize > baseSize)
    }
}
