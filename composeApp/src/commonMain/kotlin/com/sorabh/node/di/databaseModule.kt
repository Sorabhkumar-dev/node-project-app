package com.sorabh.node.di

import com.sorabh.node.AppDatabase
import com.sorabh.node.database.TaskRepository
import com.sorabh.node.database.TaskRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule = module {
    single { get<AppDatabase>().getDao() }
    single<TaskRepository> { TaskRepositoryImpl(get()) }
}

expect val platformDatabaseModule: Module
