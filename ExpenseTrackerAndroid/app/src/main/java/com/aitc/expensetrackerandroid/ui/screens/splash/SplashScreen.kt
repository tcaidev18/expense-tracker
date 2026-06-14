package com.aitc.expensetrackerandroid.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aitc.expensetrackerandroid.ui.components.state.StateHandler

@Composable
fun SplashScreen(
    onNavigateToMain: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.data?.isReady) {
        if (state.data?.isReady == true) {
            onNavigateToMain()
        }
    }

    StateHandler(
        state = state,
        onRetry = viewModel::onRetry,
        loading = { SplashContent(showLoading = true) },
    ) {
        SplashContent(showLoading = false)
    }
}
