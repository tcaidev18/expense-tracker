package com.aitc.expensetrackerandroid.core.viewmodel

import androidx.lifecycle.viewModelScope
import com.aitc.expensetrackerandroid.core.dispatcher.DispatcherProvider
import com.aitc.expensetrackerandroid.core.settings.AppLocale
import com.aitc.expensetrackerandroid.core.settings.AppThemeMode
import com.aitc.expensetrackerandroid.repository.UserSettingsRepository
import com.aitc.expensetrackerandroid.util.LocaleApplier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    dispatchers: DispatcherProvider,
    private val userSettingsRepository: UserSettingsRepository,
) : BaseViewModel(dispatchers) {

    val themeMode: StateFlow<AppThemeMode> = userSettingsRepository.observeThemeMode()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppThemeMode.SYSTEM,
        )

    val locale: StateFlow<AppLocale> = userSettingsRepository.observeLocale()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppLocale.VI,
        )

    fun setThemeMode(mode: AppThemeMode) {
        launchSafe {
            onIo { userSettingsRepository.setThemeMode(mode) }
        }
    }

    fun setLocale(locale: AppLocale) {
        launchSafe {
            onIo { userSettingsRepository.setLocale(locale) }
            LocaleApplier.apply(locale)
        }
    }
}
