package com.aitc.expensetrackerandroid.data.preferences

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class AppPreferencesTest {

    @Test
    fun setAndObserve_returnsStoredValue() = runTest {
        val key: Preferences.Key<Int> = intPreferencesKey("test_counter")
        val fakeStore = FakePreferencesDataStore()
        val preferences = AppPreferences(fakeStore)

        preferences.set(key, 42)

        assertEquals(42, preferences.observe(key, default = 0).first())
    }
}
