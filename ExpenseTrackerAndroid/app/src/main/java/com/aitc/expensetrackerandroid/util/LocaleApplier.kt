package com.aitc.expensetrackerandroid.util

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.aitc.expensetrackerandroid.core.settings.AppLocale

object LocaleApplier {
    fun apply(locale: AppLocale) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(locale.languageTag),
        )
    }
}
