package com.aitc.expensetrackerandroid.navigation

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Test

class SavedStateHandleExtTest {

    @Test
    fun requireLongArg_acceptsLongValue() {
        val handle = SavedStateHandle(mapOf("expenseId" to 1L))

        assertEquals(1L, handle.requireLongArg("expenseId"))
    }

    @Test
    fun requireLongArg_acceptsStringValue() {
        val handle = SavedStateHandle(mapOf("expenseId" to "42"))

        assertEquals(42L, handle.requireLongArg("expenseId"))
    }

    @Test
    fun requireLongArg_acceptsIntValue() {
        val handle = SavedStateHandle(mapOf("expenseId" to 7))

        assertEquals(7L, handle.requireLongArg("expenseId"))
    }
}
