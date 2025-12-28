package com.sorabh.node.di

import com.sorabh.node.data.sync.BackgroundTaskScheduler
import com.sorabh.node.data.sync.PlatformTaskScheduler
import com.sorabh.node.data.sync.SyncWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

val androidModule = module {
    workerOf(::SyncWorker)
    single<BackgroundTaskScheduler> { PlatformTaskScheduler(get()) }
}