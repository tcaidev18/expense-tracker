package com.aitc.expensetrackerandroid.core.viewmodel

import com.aitc.expensetrackerandroid.core.dispatcher.DispatcherProvider
import com.aitc.expensetrackerandroid.core.ui.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BaseViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: TestBaseViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = TestBaseViewModel(
            dispatchers = TestDispatcherProvider(testDispatcher),
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun launchSafe_setsErrorWhenBlockThrows() = runTest(testDispatcher) {
        viewModel.triggerFailure()

        advanceUntilIdle()

        assertEquals("boom", viewModel.uiState.value.errorMessage)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun launchCollect_emitsSuccess() = runTest(testDispatcher) {
        viewModel.triggerSuccess("hello")

        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.isSuccess)
        assertEquals("hello", viewModel.uiState.value.data)
    }

    private class TestBaseViewModel(
        dispatchers: DispatcherProvider,
    ) : BaseViewModel(dispatchers) {
        private val _uiState = MutableStateFlow(UiState<String>())
        val uiState = _uiState

        fun triggerFailure() {
            launchSafe(onError = { message -> _uiState.value = UiState(errorMessage = message) }) {
                error("boom")
            }
        }

        fun triggerSuccess(value: String) {
            _uiState.launchCollect(
                flow = kotlinx.coroutines.flow.flow { emit(value) },
            )
        }
    }

    private class TestDispatcherProvider(
        private val dispatcher: CoroutineDispatcher,
    ) : DispatcherProvider {
        override val io = dispatcher
        override val main = dispatcher
        override val default = dispatcher
    }
}
