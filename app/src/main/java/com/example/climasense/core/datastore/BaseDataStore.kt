package com.example.climasense.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class BaseDataStore(
    protected val dataStore: DataStore<Preferences>
) {
    protected fun <T> read(
        key: Preferences.Key<T>,
        default: T
    ): Flow<T> {
        return dataStore.data.map {
            it[key] ?: default
        }
    }

    suspend fun <T> write(
        key: Preferences.Key<T>,
        value: T
    ) {
        dataStore.edit {
            it[key] = value
        }
    }
}

object PreferenceKeys {
    val THEME_MODE = stringPreferencesKey("theme_mode")
}
