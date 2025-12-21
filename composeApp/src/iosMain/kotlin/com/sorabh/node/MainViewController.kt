package com.sorabh.node

import androidx.compose.ui.window.ComposeUIViewController
import com.sorabh.node.data.remote.httpModule
import com.sorabh.node.di.appModule
import com.sorabh.node.di.databaseModule
import com.sorabh.node.di.platformDataStoreModule
import com.sorabh.node.di.platformDatabaseModule
import com.sorabh.node.di.sharedModule
import com.sorabh.node.ui.screens.App
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    startKoin {
        modules(appModule, databaseModule, platformDatabaseModule, sharedModule,
            platformDataStoreModule, httpModule
        )
    }
    return ComposeUIViewController { App() }
}