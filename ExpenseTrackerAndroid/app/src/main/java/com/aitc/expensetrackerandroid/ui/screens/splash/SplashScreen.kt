package com.aitc.expensetrackerandroid.ui.screens.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aitc.expensetrackerandroid.ui.components.state.StateHandler

@Composable
fun SplashScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToWelcome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.data?.isReady) {
        if (state.data?.isReady == true) {
            if (state.data?.firstLaunch == true) {
                onNavigateToWelcome()
            } else {
                onNavigateToMain()
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        StateHandler(
            state = state,
            onRetry = viewModel::onRetry,
            loading = { SplashContent() },
        ) {
            SplashContent()
        }
    }
}
