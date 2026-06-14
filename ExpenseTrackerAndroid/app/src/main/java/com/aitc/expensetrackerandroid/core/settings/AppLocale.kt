package com.aitc.expensetrackerandroid.core.settings

import java.util.Locale

enum class AppLocale(
    val languageTag: String,
) {
    VI("vi"),
    EN("en"),
    ;

    fun toJavaLocale(): Locale = when (this) {
        VI -> Locale.forLanguageTag("vi-VN")
        EN -> Locale.forLanguageTag("en-US")
    }

    companion object {
        fun fromStored(value: String?): AppLocale =
            entries.firstOrNull { it.name == value } ?: VI
    }
}
