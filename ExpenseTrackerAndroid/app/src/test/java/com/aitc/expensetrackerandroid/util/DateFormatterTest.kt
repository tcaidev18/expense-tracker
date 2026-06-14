package com.aitc.expensetrackerandroid.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant
import java.time.ZoneId

class DateFormatterTest {
    @Test
    fun format_returnsDayMonthYear() {
        val formatted = DateFormatter.format(
            epochMillis = Instant.parse("2024-01-15T00:00:00Z").toEpochMilli(),
            pattern = "dd/MM/yyyy",
            zoneId = ZoneId.of("UTC"),
        )
        assertEquals("15/01/2024", formatted)
    }
}
