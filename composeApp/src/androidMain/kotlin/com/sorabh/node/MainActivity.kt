package com.sorabh.node

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sorabh.node.data.remote.httpModule
import com.sorabh.node.data.sync.BackgroundTaskScheduler
import com.sorabh.node.di.androidModule
import com.sorabh.node.di.appModule
import com.sorabh.node.di.databaseModule
import com.sorabh.node.di.platformDataStoreModule
import com.sorabh.node.di.platformDatabaseModule
import com.sorabh.node.di.sharedModule
import com.sorabh.node.ui.screens.App
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    val taskScheduler: BackgroundTaskScheduler by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            workManagerFactory()
            modules(
                androidModule,
                appModule,
                databaseModule,
                platformDatabaseModule,
                platformDataStoreModule,
                sharedModule,
                httpModule
            )
        }
        taskScheduler.scheduleSync()

        setContent {
            App()
        }
    }
}
