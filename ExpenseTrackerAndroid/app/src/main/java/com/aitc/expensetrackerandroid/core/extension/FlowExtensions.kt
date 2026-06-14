package com.aitc.expensetrackerandroid.core.extension

import com.aitc.expensetrackerandroid.core.dispatcher.DispatcherProvider
import com.aitc.expensetrackerandroid.core.result.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

fun <T> Flow<T>.flowOnIo(dispatchers: DispatcherProvider): Flow<T> = flowOn(dispatchers.io)

fun <T> Flow<T>.catchEmitError(
    onError: (Throwable) -> String = { it.message ?: "Unknown error" },
): Flow<AppResult<T>> = map<T, AppResult<T>> { AppResult.Success(it) }
    .catch { emit(AppResult.Error(onError(it), it)) }

fun <T, R> Flow<T>.mapToAppResult(transform: (T) -> R): Flow<AppResult<R>> =
    map<T, AppResult<R>> { AppResult.Success(transform(it)) }
        .catch { emit(AppResult.Error(it.message ?: "Unknown error", it)) }
