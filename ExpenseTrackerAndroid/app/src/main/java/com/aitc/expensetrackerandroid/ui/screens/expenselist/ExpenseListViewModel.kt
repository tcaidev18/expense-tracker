package com.aitc.expensetrackerandroid.ui.screens.expenselist

import com.aitc.expensetrackerandroid.core.dispatcher.DispatcherProvider
import com.aitc.expensetrackerandroid.core.ui.UiState
import com.aitc.expensetrackerandroid.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    dispatchers: DispatcherProvider,
) : BaseViewModel(dispatchers) {

    private val _uiState = MutableStateFlow(UiState<ExpenseListUiState>())
    val uiState: StateFlow<UiState<ExpenseListUiState>> = _uiState.asStateFlow()

    init {
        load()
    }

    fun onRetry() = load()

    private fun load() {
        _uiState.launchCollect(flow = flowOf(ExpenseListUiState()))
    }
}

data class ExpenseListUiState(
    val items: List<ExpenseListItemUiModel> = emptyList(),
)

data class ExpenseListItemUiModel(
    val id: Long,
    val title: String,
    val amountLabel: String,
)
