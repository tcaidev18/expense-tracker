package com.aitc.expensetrackerandroid.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateFormatter {
    private val defaultLocale = Locale.forLanguageTag("vi-VN")
    private val defaultZone = ZoneId.systemDefault()

    fun format(
        epochMillis: Long,
        pattern: String = "dd/MM/yyyy",
        locale: Locale = defaultLocale,
        zoneId: ZoneId = defaultZone,
    ): String {
        val formatter = DateTimeFormatter.ofPattern(pattern, locale)
        return Instant.ofEpochMilli(epochMillis)
            .atZone(zoneId)
            .format(formatter)
    }
}
