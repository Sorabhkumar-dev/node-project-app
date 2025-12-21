package com.sorabh.node.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.sorabh.node.data.datastore.DATA_STORE_FILE_NAME
import com.sorabh.node.data.datastore.createDataStore

// Context is needed to find the file path
fun createDataStore(context: Context): DataStore<Preferences> {
    return createDataStore {
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    }
}