package com.aitc.expensetrackerandroid.repository

import com.aitc.expensetrackerandroid.core.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext

abstract class BaseRepository(
    private val dispatchers: DispatcherProvider,
) {
    protected suspend fun <T> onIo(block: suspend () -> T): T =
        withContext(dispatchers.io) { block() }
}
