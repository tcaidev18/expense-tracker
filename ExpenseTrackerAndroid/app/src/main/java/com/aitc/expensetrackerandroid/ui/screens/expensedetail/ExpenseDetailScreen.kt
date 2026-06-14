package com.aitc.expensetrackerandroid.ui.screens.expensedetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aitc.expensetrackerandroid.R
import com.aitc.expensetrackerandroid.ui.components.state.StateHandler
import com.aitc.expensetrackerandroid.ui.theme.Spacing

@Composable
fun ExpenseDetailScreen(
    viewModel: ExpenseDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    StateHandler(
        state = state,
        onRetry = viewModel::onRetry,
    ) { data ->
        ExpenseDetailContent(data = data)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpenseDetailContent(data: ExpenseDetailUiState) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.expense_detail_title))
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(Spacing.medium),
        ) {
            Text(
                text = stringResource(R.string.expense_detail_id_label, data.expenseId),
                style = MaterialTheme.typography.titleMedium,
            )
            if (data.title.isNotEmpty()) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = Spacing.small),
                )
            }
            if (data.amountLabel.isNotEmpty()) {
                Text(
                    text = data.amountLabel,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = Spacing.small),
                )
            }
        }
    }
}
