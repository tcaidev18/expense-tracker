package com.aitc.expensetrackerandroid.ui

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.aitc.expensetrackerandroid.core.viewmodel.AppViewModel
import com.aitc.expensetrackerandroid.navigation.AppNavGraph
import com.aitc.expensetrackerandroid.ui.theme.ExpenseTrackerAndroidTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AppRoot(
    viewModel: AppViewModel = hiltViewModel(),
) {
    val activity = LocalActivity.current as ComponentActivity
    val windowSizeClass = calculateWindowSizeClass(activity)
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val darkTheme = themeMode.resolve(isSystemInDarkTheme())

    ExpenseTrackerAndroidTheme(
        darkTheme = darkTheme,
        windowSizeClass = windowSizeClass,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            AppNavGraph(navController = rememberNavController())
        }
    }
}
