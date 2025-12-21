package com.sorabh.node

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sorabh.node.data.remote.httpModule
import com.sorabh.node.di.appModule
import com.sorabh.node.di.databaseModule
import com.sorabh.node.di.platformDataStoreModule
import com.sorabh.node.di.platformDatabaseModule
import com.sorabh.node.di.sharedModule
import com.sorabh.node.ui.screens.App
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule, databaseModule, platformDatabaseModule,platformDataStoreModule,sharedModule,
                httpModule
            )
        }

        setContent {
            App()
        }
    }
}
