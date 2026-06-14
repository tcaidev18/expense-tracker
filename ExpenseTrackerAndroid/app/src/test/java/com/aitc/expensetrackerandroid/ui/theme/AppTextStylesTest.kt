package com.aitc.expensetrackerandroid.ui.theme

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AppTextStylesTest {

    @Test
    fun appTextStyles_derivesFromTypography() {
        val typography = baseTypography
        val styles = appTextStyles(typography)

        assertEquals(typography.headlineLarge, styles.screenTitle)
        assertEquals(typography.titleLarge, styles.sectionHeader)
        assertEquals(typography.titleMedium, styles.listItemTitle)
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
