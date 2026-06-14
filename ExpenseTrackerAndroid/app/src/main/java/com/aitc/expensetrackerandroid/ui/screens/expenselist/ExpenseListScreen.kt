package com.aitc.expensetrackerandroid.ui.screens.expenselist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.material3.Button
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
fun ExpenseListScreen(
    onOpenExpenseDetail: (Long) -> Unit,
    viewModel: ExpenseListViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    StateHandler(
        state = state,
        onRetry = viewModel::onRetry,
    ) { data ->
        ExpenseListContent(
            data = data,
            onOpenExpenseDetail = onOpenExpenseDetail,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpenseListContent(
    data: ExpenseListUiState,
    onOpenExpenseDetail: (Long) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.expense_list_title))
                },
            )
        },
    ) { innerPadding ->
        if (data.items.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.expense_list_empty),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Button(
                    onClick = { onOpenExpenseDetail(SAMPLE_EXPENSE_ID) },
                    modifier = Modifier.padding(top = Spacing.medium),
                ) {
                    Text(text = stringResource(R.string.expense_list_open_sample_detail))
                }
            }
        } else {
            Text(
                text = stringResource(R.string.expense_list_placeholder),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

private const val SAMPLE_EXPENSE_ID = 1L
