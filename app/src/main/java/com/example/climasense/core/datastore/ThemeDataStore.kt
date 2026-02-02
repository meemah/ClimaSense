package com.example.climasense.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

enum class ThemeMode {
    SYSTEM,
    LIGHT, DARK
}

@Singleton
class ThemeDataStore
@Inject constructor(
    dataStore: DataStore<Preferences>
) : BaseDataStore(dataStore) {
    val themeMode: Flow<ThemeMode> = read(
        PreferenceKeys.THEME_MODE,
        ThemeMode.SYSTEM.name,
    )
        .map {
            ThemeMode.valueOf(it)
        }

    suspend fun setTheme(mode: ThemeMode) {
        write(PreferenceKeys.THEME_MODE, mode.name)
    }
}