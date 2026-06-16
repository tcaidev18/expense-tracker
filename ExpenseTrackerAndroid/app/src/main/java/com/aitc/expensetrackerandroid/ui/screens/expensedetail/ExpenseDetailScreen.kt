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
import com.aitc.expensetrackerandroid.ui.theme.appDimens
import com.aitc.expensetrackerandroid.ui.theme.appTextStyles

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
    val dimens = MaterialTheme.appDimens
    val textStyles = MaterialTheme.appTextStyles

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.expense_detail_title),
                        style = textStyles.sectionHeader,
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dimens.screenPadding),
        ) {
            Text(
                text = stringResource(R.string.expense_detail_id_label, data.expenseId),
                style = textStyles.listItemTitle,
            )
            if (data.title.isNotEmpty()) {
                Text(
                    text = data.title,
                    style = textStyles.listItemSubtitle,
                    modifier = Modifier.padding(top = dimens.spacingSm),
                )
            }
            if (data.amountLabel.isNotEmpty()) {
                Text(
                    text = data.amountLabel,
                    style = textStyles.amountStandard,
                    modifier = Modifier.padding(top = dimens.spacingSm),
                )
            }
        }
    }
}
