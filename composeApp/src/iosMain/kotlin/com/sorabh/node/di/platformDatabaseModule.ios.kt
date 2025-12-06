package com.sorabh.node.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.sorabh.node.AppDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val platformDatabaseModule: Module = module {
    single<AppDatabase> {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = true, // This is the crucial fix
            error = null
        )!!
        val dbFile = documentDirectory.path!! + "/tasks.db"

        Room.databaseBuilder<AppDatabase>(
            name = dbFile
        )
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}