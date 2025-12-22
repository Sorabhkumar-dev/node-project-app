package com.sorabh.node.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.dsl.module

actual val platformDataStoreModule = module {
    single<DataStore<Preferences>> {
        com.sorabh.node.datastore.createDataStore() // This function will be defined to create the datastore for iOS
    }
}