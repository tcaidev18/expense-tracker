package com.aitc.expensetrackerandroid.repository

import com.aitc.expensetrackerandroid.core.dispatcher.DispatcherProvider
import com.aitc.expensetrackerandroid.core.settings.AppLocale
import com.aitc.expensetrackerandroid.core.settings.AppThemeMode
import com.aitc.expensetrackerandroid.data.preferences.AppPreferences
import com.aitc.expensetrackerandroid.data.preferences.PreferenceKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSettingsRepository @Inject constructor(
    private val appPreferences: AppPreferences,
    dispatchers: DispatcherProvider,
) : BaseRepository(dispatchers) {

    fun observeLocale(): Flow<AppLocale> =
        appPreferences.observe(PreferenceKeys.APP_LOCALE, AppLocale.VI.name)
            .map(AppLocale::fromStored)

    fun observeThemeMode(): Flow<AppThemeMode> =
        appPreferences.observe(PreferenceKeys.THEME_MODE, AppThemeMode.SYSTEM.name)
            .map(AppThemeMode::fromStored)

    suspend fun getLocale(): AppLocale = onIo {
        appPreferences.observe(PreferenceKeys.APP_LOCALE, AppLocale.VI.name)
            .first()
            .let(AppLocale::fromStored)
    }

    suspend fun setLocale(locale: AppLocale) = onIo {
        appPreferences.set(PreferenceKeys.APP_LOCALE, locale.name)
    }

    suspend fun setThemeMode(mode: AppThemeMode) = onIo {
        appPreferences.set(PreferenceKeys.THEME_MODE, mode.name)
    }
}
