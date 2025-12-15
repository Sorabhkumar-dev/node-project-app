package com.sorabh.node.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

// 1. Create a function to build the DataStore.
// It accepts a producer that returns the platform-specific file path.
fun createDataStore(producePath: () -> String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        corruptionHandler = null,
        migrations = emptyList(),
        produceFile = { producePath().toPath() }
    )
}

// 2. Define the exact file name for consistency
internal const val DATA_STORE_FILE_NAME = "app_preferences.preferences_pb"