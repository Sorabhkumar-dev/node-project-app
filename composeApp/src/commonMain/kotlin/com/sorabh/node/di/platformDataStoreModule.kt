package com.sorabh.node.di

import com.sorabh.node.data.datastore.NodePreference
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDataStoreModule: Module

val sharedModule = module {
    // Inject the AppPreferences class we created in Step 4
    single { NodePreference(get()) }
}