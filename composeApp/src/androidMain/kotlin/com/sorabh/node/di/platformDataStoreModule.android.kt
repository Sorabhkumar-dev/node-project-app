package com.sorabh.node.di

import com.sorabh.node.datastore.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDataStoreModule: Module
    get() = module {
        single { createDataStore(androidContext()) }
    }