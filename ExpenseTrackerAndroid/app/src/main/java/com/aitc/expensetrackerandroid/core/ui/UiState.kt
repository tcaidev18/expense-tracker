package com.aitc.expensetrackerandroid.core.ui

data class UiState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val errorMessage: String? = null,
) {
    val isEmpty: Boolean
        get() = !isLoading && errorMessage == null && data == null

    val isSuccess: Boolean
        get() = !isLoading && errorMessage == null && data != null
}

fun <T> UiState<T>.loading(): UiState<T> = copy(isLoading = true, errorMessage = null)

fun <T> UiState<T>.success(data: T): UiState<T> = copy(isLoading = false, data = data, errorMessage = null)

fun <T> UiState<T>.error(message: String): UiState<T> = copy(isLoading = false, errorMessage = message)
