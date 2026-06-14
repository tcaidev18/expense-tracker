package com.aitc.expensetrackerandroid.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.aitc.expensetrackerandroid.ui.components.state.StateHandler
import com.aitc.expensetrackerandroid.ui.theme.SplashBackground

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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SplashBackground,
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
