package com.aitc.expensetrackerandroid.core.extension

import com.aitc.expensetrackerandroid.core.dispatcher.DispatcherProvider
import com.aitc.expensetrackerandroid.core.result.AppResult
import kotlinx.coroutines.withContext

suspend fun <T> runCatchingIo(
    dispatchers: DispatcherProvider,
    block: suspend () -> T,
): AppResult<T> = withContext(dispatchers.io) {
    try {
        AppResult.Success(block())
    } catch (e: Exception) {
        AppResult.Error(e.message ?: "Unknown error", e)
    }
}
