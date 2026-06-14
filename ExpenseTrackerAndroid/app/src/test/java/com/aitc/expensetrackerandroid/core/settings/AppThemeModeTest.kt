package com.aitc.expensetrackerandroid.core.settings

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AppThemeModeTest {

    @Test
    fun resolve_system_followsSystemDark() {
        assertTrue(AppThemeMode.SYSTEM.resolve(isSystemDark = true))
        assertFalse(AppThemeMode.SYSTEM.resolve(isSystemDark = false))
    }

    @Test
    fun resolve_light_alwaysLight() {
        assertFalse(AppThemeMode.LIGHT.resolve(isSystemDark = true))
        assertFalse(AppThemeMode.LIGHT.resolve(isSystemDark = false))
    }

    @Test
    fun resolve_dark_alwaysDark() {
        assertTrue(AppThemeMode.DARK.resolve(isSystemDark = true))
        assertTrue(AppThemeMode.DARK.resolve(isSystemDark = false))
    }

    @Test
    fun fromStored_unknownDefaultsToSystem() {
        assertEquals(AppThemeMode.SYSTEM, AppThemeMode.fromStored(null))
        assertEquals(AppThemeMode.SYSTEM, AppThemeMode.fromStored("invalid"))
    }

    @Test
    fun fromStored_parsesKnownValues() {
        assertEquals(AppThemeMode.DARK, AppThemeMode.fromStored("DARK"))
    }
}
