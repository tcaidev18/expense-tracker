package com.aitc.expensetrackerandroid.ui.theme

import org.junit.Assert.assertEquals
import org.junit.Test

class ColorSchemeTest {

    @Test
    fun lightAppColorScheme_mapsPrimaryBlueRamp() {
        val scheme = lightAppColorScheme()

        assertEquals(Primary, scheme.primary)
        assertEquals(OnPrimary, scheme.onPrimary)
        assertEquals(PrimaryContainer, scheme.primaryContainer)
        assertEquals(OnPrimaryContainer, scheme.onPrimaryContainer)
    }

    @Test
    fun darkAppColorScheme_mapsPrimaryBlueDarkRamp() {
        val scheme = darkAppColorScheme()

        assertEquals(PrimaryDark, scheme.primary)
        assertEquals(OnPrimaryDark, scheme.onPrimary)
        assertEquals(PrimaryContainerDark, scheme.primaryContainer)
        assertEquals(OnPrimaryContainerDark, scheme.onPrimaryContainer)
    }

    @Test
    fun lightAppColorScheme_keepsTealAsSecondary() {
        val scheme = lightAppColorScheme()

        assertEquals(BrandSecondary, scheme.secondary)
        assertEquals(BrandTertiary, scheme.tertiary)
    }
}
