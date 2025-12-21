package com.sorabh.node.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.sorabh.node.data.datastore.createDataStore
import org.koin.dsl.module

actual val platformDataStoreModule = module {
    single<DataStore<Preferences>> {
        createDataStore() // This function will be defined to create the datastore for iOS
    }
}