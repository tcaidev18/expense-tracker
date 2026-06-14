package com.aitc.expensetrackerandroid.ui.components.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.aitc.expensetrackerandroid.R
import com.aitc.expensetrackerandroid.core.ui.UiState
import com.aitc.expensetrackerandroid.ui.theme.Spacing

@Composable
fun <T> StateHandler(
    state: UiState<T>,
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier,
    empty: @Composable () -> Unit = { EmptyContent() },
    loading: @Composable () -> Unit = { LoadingContent() },
    error: @Composable (String) -> Unit = { message -> ErrorContent(message, onRetry) },
    content: @Composable (T) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading -> loading()
            state.errorMessage != null -> error(state.errorMessage)
            state.data != null -> content(state.data)
            else -> empty()
        }
    }
}

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Spacing.large),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
        )
        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = Spacing.medium),
        ) {
            Text(text = stringResource(R.string.action_retry))
        }
    }
}

@Composable
fun EmptyContent(
    message: String = stringResource(R.string.state_empty),
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
