package com.aitc.expensetrackerandroid.ui.screens.debug

import com.aitc.expensetrackerandroid.core.dispatcher.DispatcherProvider
import com.aitc.expensetrackerandroid.core.settings.AppLocale
import com.aitc.expensetrackerandroid.core.settings.AppThemeMode
import com.aitc.expensetrackerandroid.core.ui.UiState
import com.aitc.expensetrackerandroid.core.viewmodel.BaseViewModel
import com.aitc.expensetrackerandroid.repository.UserSettingsRepository
import com.aitc.expensetrackerandroid.util.LocaleApplier
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

@HiltViewModel
class DebugViewModel @Inject constructor(
    dispatchers: DispatcherProvider,
    private val userSettingsRepository: UserSettingsRepository,
) : BaseViewModel(dispatchers) {

    private val _uiState = MutableStateFlow(UiState<DebugUiState>())
    val uiState: StateFlow<UiState<DebugUiState>> = _uiState.asStateFlow()

    init {
        observeSettings()
    }

    fun onRetry() = observeSettings()

    fun onThemeModeSelected(mode: AppThemeMode) {
        launchSafe {
            onIo { userSettingsRepository.setThemeMode(mode) }
        }
    }

    fun onLocaleSelected(locale: AppLocale) {
        launchSafe {
            onIo { userSettingsRepository.setLocale(locale) }
            withContext(dispatchers.main) {
                LocaleApplier.apply(locale)
            }
        }
    }

    private fun observeSettings() {
        _uiState.launchCollect(
            flow = combine(
                userSettingsRepository.observeThemeMode(),
                userSettingsRepository.observeLocale(),
            ) { themeMode, locale ->
                DebugUiState(
                    themeMode = themeMode,
                    locale = locale,
                )
            },
        )
    }
}

data class DebugUiState(
    val themeMode: AppThemeMode = AppThemeMode.SYSTEM,
    val locale: AppLocale = AppLocale.VI,
)
