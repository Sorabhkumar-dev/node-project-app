package com.sorabh.node.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map

class NodePreference(private val dataStore: DataStore<Preferences>) {

    val readTheme = dataStore.data.map { preferences ->
        preferences[NodePrefKeys.LIGHT_DARK_THEME] ?: false
    }

    suspend fun writeTheme(isLight: Boolean) {
        dataStore.edit { preferences ->
            preferences[NodePrefKeys.LIGHT_DARK_THEME] = isLight
        }
    }
}