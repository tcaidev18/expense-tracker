package com.aitc.expensetrackerandroid.ui.screens.expensedetail

import androidx.lifecycle.SavedStateHandle
import com.aitc.expensetrackerandroid.core.dispatcher.DispatcherProvider
import com.aitc.expensetrackerandroid.core.ui.UiState
import com.aitc.expensetrackerandroid.core.viewmodel.BaseViewModel
import com.aitc.expensetrackerandroid.navigation.Route
import com.aitc.expensetrackerandroid.navigation.requireLongArg
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    dispatchers: DispatcherProvider,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel(dispatchers) {

    private val expenseId: Long = savedStateHandle.requireLongArg(Route.ExpenseDetail.ARG_EXPENSE_ID)

    private val _uiState = MutableStateFlow(UiState<ExpenseDetailUiState>())
    val uiState: StateFlow<UiState<ExpenseDetailUiState>> = _uiState.asStateFlow()

    init {
        load()
    }

    fun onRetry() = load()

    private fun load() {
        _uiState.launchCollect(
            flow = flowOf(
                ExpenseDetailUiState(
                    expenseId = expenseId,
                ),
            ),
        )
    }
}

data class ExpenseDetailUiState(
    val expenseId: Long,
    val title: String = "",
    val amountLabel: String = "",
)
