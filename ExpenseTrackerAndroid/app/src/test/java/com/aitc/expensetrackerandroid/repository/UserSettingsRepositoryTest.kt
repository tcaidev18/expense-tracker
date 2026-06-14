package com.aitc.expensetrackerandroid.repository

import com.aitc.expensetrackerandroid.core.dispatcher.DefaultDispatcherProvider
import com.aitc.expensetrackerandroid.core.settings.AppLocale
import com.aitc.expensetrackerandroid.core.settings.AppThemeMode
import com.aitc.expensetrackerandroid.data.preferences.AppPreferences
import com.aitc.expensetrackerandroid.data.preferences.FakePreferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserSettingsRepositoryTest {

    private val repository = UserSettingsRepository(
        appPreferences = AppPreferences(FakePreferencesDataStore()),
        dispatchers = DefaultDispatcherProvider(),
    )

    @Test
    fun observeLocale_defaultsToVi() = runTest {
        assertEquals(AppLocale.VI, repository.observeLocale().first())
    }

    @Test
    fun setLocale_persistsValue() = runTest {
        repository.setLocale(AppLocale.EN)

        assertEquals(AppLocale.EN, repository.observeLocale().first())
        assertEquals(AppLocale.EN, repository.getLocale())
    }

    @Test
    fun observeThemeMode_defaultsToSystem() = runTest {
        assertEquals(AppThemeMode.SYSTEM, repository.observeThemeMode().first())
    }

    @Test
    fun setThemeMode_persistsValue() = runTest {
        repository.setThemeMode(AppThemeMode.DARK)

        assertEquals(AppThemeMode.DARK, repository.observeThemeMode().first())
    }
}
