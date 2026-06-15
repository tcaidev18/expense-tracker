package com.aitc.expensetrackerandroid.ui.screens.debug

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aitc.expensetrackerandroid.R
import com.aitc.expensetrackerandroid.core.settings.AppLocale
import com.aitc.expensetrackerandroid.core.settings.AppThemeMode
import com.aitc.expensetrackerandroid.ui.components.state.StateHandler
import com.aitc.expensetrackerandroid.ui.theme.appDimens
import com.aitc.expensetrackerandroid.ui.theme.appTextStyles

@Composable
fun DebugScreen(
    onBack: () -> Unit,
    viewModel: DebugViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    StateHandler(
        state = state,
        onRetry = viewModel::onRetry,
    ) { data ->
        DebugContent(
            data = data,
            onBack = onBack,
            onThemeModeSelected = viewModel::onThemeModeSelected,
            onLocaleSelected = viewModel::onLocaleSelected,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DebugContent(
    data: DebugUiState,
    onBack: () -> Unit,
    onThemeModeSelected: (AppThemeMode) -> Unit,
    onLocaleSelected: (AppLocale) -> Unit,
) {
    val dimens = MaterialTheme.appDimens
    val textStyles = MaterialTheme.appTextStyles

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.debug_title),
                        style = textStyles.sectionHeader,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_back),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dimens.screenPadding),
            verticalArrangement = Arrangement.spacedBy(dimens.spacingLg),
        ) {
            Text(
                text = stringResource(R.string.debug_theme_section),
                style = textStyles.sectionHeader,
            )
            ThemeModeSelector(
                selectedMode = data.themeMode,
                onThemeModeSelected = onThemeModeSelected,
            )

            Text(
                text = stringResource(R.string.debug_language_section),
                style = textStyles.sectionHeader,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.spacingSm),
            ) {
                AppLocale.entries.forEach { locale ->
                    FilterChip(
                        selected = data.locale == locale,
                        onClick = { onLocaleSelected(locale) },
                        label = {
                            Text(
                                text = stringResource(locale.labelRes()),
                                style = textStyles.listItemSubtitle,
                            )
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeModeSelector(
    selectedMode: AppThemeMode,
    onThemeModeSelected: (AppThemeMode) -> Unit,
) {
    val modes = AppThemeMode.entries
    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
        modes.forEachIndexed { index, mode ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = modes.size,
                ),
                onClick = { onThemeModeSelected(mode) },
                selected = selectedMode == mode,
                label = {
                    Text(text = stringResource(mode.labelRes()))
                },
            )
        }
    }
}

@Composable
private fun AppThemeMode.labelRes(): Int = when (this) {
    AppThemeMode.SYSTEM -> R.string.debug_theme_system
    AppThemeMode.LIGHT -> R.string.debug_theme_light
    AppThemeMode.DARK -> R.string.debug_theme_dark
}

@Composable
private fun AppLocale.labelRes(): Int = when (this) {
    AppLocale.VI -> R.string.debug_language_vi
    AppLocale.EN -> R.string.debug_language_en
}
