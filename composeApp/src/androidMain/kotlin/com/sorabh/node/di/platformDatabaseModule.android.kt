package com.sorabh.node.di

import androidx.room.Room
import com.sorabh.node.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDatabaseModule: Module = module {
    single<AppDatabase> {
        val context = androidContext()
        val dbFile = context.getDatabasePath("tasks.db")
        Room.databaseBuilder<AppDatabase>(
            context = context,
            name = dbFile.absolutePath
        ).build()
    }
}