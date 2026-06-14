package com.aitc.expensetrackerandroid.ui.screens.welcome

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aitc.expensetrackerandroid.R
import com.aitc.expensetrackerandroid.ui.components.state.StateHandler

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    StateHandler(
        state = state,
        onRetry = viewModel::onRetry,
    ) { data ->
        WelcomeContent(data = data)
    }
}

@Composable
private fun WelcomeContent(data: WelcomeUiState) {
    // TODO: UI
    Text(text = stringResource(R.string.welcome_title))
}
