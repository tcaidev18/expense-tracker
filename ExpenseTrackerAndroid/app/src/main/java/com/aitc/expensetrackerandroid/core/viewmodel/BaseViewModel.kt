package com.aitc.expensetrackerandroid.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aitc.expensetrackerandroid.core.dispatcher.DispatcherProvider
import com.aitc.expensetrackerandroid.core.ui.UiState
import com.aitc.expensetrackerandroid.core.ui.error
import com.aitc.expensetrackerandroid.core.ui.loading
import com.aitc.expensetrackerandroid.core.ui.success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel(
    protected val dispatchers: DispatcherProvider,
) : ViewModel() {

    protected fun <T> MutableStateFlow<UiState<T>>.updateState(block: UiState<T>.() -> UiState<T>) {
        update(block)
    }

    protected fun launchSafe(
        onError: (String) -> Unit = {},
        block: suspend () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }

    protected fun <T> MutableStateFlow<UiState<T>>.launchCollect(
        flow: Flow<T>,
        onError: (String) -> Unit = { message -> updateState { error(message) } },
    ) {
        viewModelScope.launch {
            flow
                .onStart { updateState { loading() } }
                .catch { e -> onError(e.message ?: "Unknown error") }
                .collect { data -> updateState { success(data) } }
        }
    }

    protected suspend fun <T> onIo(block: suspend () -> T): T =
        withContext(dispatchers.io) { block() }
}
