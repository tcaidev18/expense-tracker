package com.aitc.expensetrackerandroid.core.settings

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class AppLocaleTest {

    @Test
    fun fromStored_unknownDefaultsToVi() {
        assertEquals(AppLocale.VI, AppLocale.fromStored(null))
        assertEquals(AppLocale.VI, AppLocale.fromStored("invalid"))
    }

    @Test
    fun fromStored_parsesKnownValues() {
        assertEquals(AppLocale.EN, AppLocale.fromStored("EN"))
    }

    @Test
    fun toJavaLocale_mapsRegionalTags() {
        assertEquals(Locale.forLanguageTag("vi-VN"), AppLocale.VI.toJavaLocale())
        assertEquals(Locale.forLanguageTag("en-US"), AppLocale.EN.toJavaLocale())
    }

    @Test
    fun languageTag_matchesBcp47() {
        assertEquals("vi", AppLocale.VI.languageTag)
        assertEquals("en", AppLocale.EN.languageTag)
    }
}
