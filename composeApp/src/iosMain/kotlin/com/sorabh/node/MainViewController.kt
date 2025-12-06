package com.sorabh.node

import androidx.compose.ui.window.ComposeUIViewController
import com.sorabh.node.di.appModule
import com.sorabh.node.di.databaseModule
import com.sorabh.node.di.platformDatabaseModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    startKoin {
        modules(appModule, databaseModule, platformDatabaseModule)
    }
    App()
}