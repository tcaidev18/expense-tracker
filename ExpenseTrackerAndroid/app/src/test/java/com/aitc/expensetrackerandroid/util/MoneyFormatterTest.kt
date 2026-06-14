package com.aitc.expensetrackerandroid.util

import org.junit.Assert.assertTrue
import org.junit.Test

class MoneyFormatterTest {
    @Test
    fun formatVnd_hasNoDecimalPlaces() {
        val formatted = MoneyFormatter.format(amount = 150_000L, currencyCode = "VND")
        assertTrue(formatted.contains("150"))
    }
}
