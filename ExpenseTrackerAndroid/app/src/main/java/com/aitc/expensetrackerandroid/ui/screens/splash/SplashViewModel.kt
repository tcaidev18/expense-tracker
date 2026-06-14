package com.aitc.expensetrackerandroid.ui.screens.splash

import com.aitc.expensetrackerandroid.core.dispatcher.DispatcherProvider
import com.aitc.expensetrackerandroid.core.ui.UiState
import com.aitc.expensetrackerandroid.core.ui.error
import com.aitc.expensetrackerandroid.core.ui.success
import com.aitc.expensetrackerandroid.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class SplashViewModel @Inject constructor(
    dispatchers: DispatcherProvider,
) : BaseViewModel(dispatchers) {

    private val _uiState = MutableStateFlow(UiState<SplashUiState>(isLoading = true))
    val uiState: StateFlow<UiState<SplashUiState>> = _uiState.asStateFlow()

    init {
        load()
    }

    fun onRetry() = load()

    private fun load() {
        launchSafe(onError = { message -> _uiState.updateState { error(message) } }) {
            _uiState.updateState { copy(isLoading = true, errorMessage = null) }
            onIo { delay(SPLASH_DELAY_MS) }
            _uiState.updateState { success(SplashUiState(isReady = true)) }
        }
    }

    private companion object {
        const val SPLASH_DELAY_MS = 1_500L
    }
}

data class SplashUiState(
    val isReady: Boolean = false,
)
