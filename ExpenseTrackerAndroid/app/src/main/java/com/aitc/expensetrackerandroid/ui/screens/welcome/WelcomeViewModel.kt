package com.aitc.expensetrackerandroid.ui.screens.welcome

import androidx.annotation.DrawableRes
import com.aitc.expensetrackerandroid.R
import com.aitc.expensetrackerandroid.core.dispatcher.DispatcherProvider
import com.aitc.expensetrackerandroid.core.ui.UiState
import com.aitc.expensetrackerandroid.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    dispatchers: DispatcherProvider,
) : BaseViewModel(dispatchers) {

    private val _uiState = MutableStateFlow(UiState<WelcomeUiState>())
    val uiState: StateFlow<UiState<WelcomeUiState>> = _uiState.asStateFlow()

    init {
        load()
    }

    fun onRetry() = load()

    private fun load() {
        _uiState.launchCollect(
            flow = flowOf(
                WelcomeUiState(
                    slides = listOf(
                        WelcomeSlide(
                            title = "Track Every Expense",
                            description = "Record your daily spending effortlessly\nand stay aware of where your money goes.",
                            image = R.drawable.welcome_1
                        ),
                        WelcomeSlide(
                            title = "Plan Smarter Budgets",
                            description = "Set monthly budgets, monitor your progress,\nand avoid overspending with ease.",
                            image = R.drawable.welcome_2
                        ),
                        WelcomeSlide(
                            title = "Gain Financial Insights",
                            description = "Visualize spending trends, analyze reports,\nand make informed financial decisions.",
                            image = R.drawable.welcome_3
                        )
                    )
                )
            )
        )
    }
}

data class WelcomeSlide(
    val title: String,
    val description: String,
    @param:DrawableRes val image: Int,
)

data class WelcomeUiState(
    val slides: List<WelcomeSlide> = emptyList(),
    val currentPage: Int = 0,
    val placeholder: String = "",
)
