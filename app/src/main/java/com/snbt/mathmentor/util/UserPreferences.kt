package com.snbt.mathmentor.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val DB_INITIALIZED = booleanPreferencesKey("db_initialized")
    }

    val isOnboardingDone: Flow<Boolean> = dataStore.data.map { it[ONBOARDING_DONE] ?: false }
    val isDarkMode: Flow<Boolean> = dataStore.data.map { it[DARK_MODE] ?: false }
    val isDbInitialized: Flow<Boolean> = dataStore.data.map { it[DB_INITIALIZED] ?: false }

    suspend fun setOnboardingDone() = dataStore.edit { it[ONBOARDING_DONE] = true }
    suspend fun setDarkMode(enabled: Boolean) = dataStore.edit { it[DARK_MODE] = enabled }
    suspend fun setDbInitialized() = dataStore.edit { it[DB_INITIALIZED] = true }
    suspend fun resetAll() = dataStore.edit { it.clear() }
}
