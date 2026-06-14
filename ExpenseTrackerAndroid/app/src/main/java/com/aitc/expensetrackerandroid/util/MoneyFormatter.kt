package com.aitc.expensetrackerandroid.util

import com.aitc.expensetrackerandroid.core.settings.AppLocale
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

object MoneyFormatter {
    private const val DEFAULT_CURRENCY = "VND"

    fun format(
        amount: Long,
        currencyCode: String = DEFAULT_CURRENCY,
        locale: Locale = AppLocale.VI.toJavaLocale(),
    ): String {
        val format = NumberFormat.getCurrencyInstance(locale).apply {
            currency = Currency.getInstance(currencyCode)
            maximumFractionDigits = if (currencyCode == DEFAULT_CURRENCY) 0 else 2
            minimumFractionDigits = if (currencyCode == DEFAULT_CURRENCY) 0 else 2
        }
        return format.format(amount)
    }
}
