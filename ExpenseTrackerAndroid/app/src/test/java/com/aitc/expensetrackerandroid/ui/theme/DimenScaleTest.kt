package com.aitc.expensetrackerandroid.ui.theme

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import org.junit.Assert.assertEquals
import org.junit.Test

class DimenScaleTest {

    @Test
    fun dimenScaleFor_compact_isBaseline() {
        val scale = dimenScaleFor(WindowWidthSizeClass.Compact)
        assertEquals(1.0f, scale.textScale)
        assertEquals(1.0f, scale.dimensScale)
    }

    @Test
    fun dimenScaleFor_medium_scalesUp() {
        val scale = dimenScaleFor(WindowWidthSizeClass.Medium)
        assertEquals(1.08f, scale.textScale)
        assertEquals(1.12f, scale.dimensScale)
    }

    @Test
    fun dimenScaleFor_expanded_scalesUpMost() {
        val scale = dimenScaleFor(WindowWidthSizeClass.Expanded)
        assertEquals(1.15f, scale.textScale)
        assertEquals(1.2f, scale.dimensScale)
    }
}
