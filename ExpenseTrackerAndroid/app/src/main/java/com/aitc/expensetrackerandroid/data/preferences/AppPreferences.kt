package com.aitc.expensetrackerandroid.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    fun <T> observe(key: Preferences.Key<T>, default: T): Flow<T> =
        dataStore.data.map { preferences -> preferences[key] ?: default }

    suspend fun <T> set(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences -> preferences[key] = value }
    }

    suspend fun <T> remove(key: Preferences.Key<T>) {
        dataStore.edit { preferences -> preferences.remove(key) }
    }
}
