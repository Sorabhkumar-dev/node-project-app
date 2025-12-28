package com.sorabh.node.data.sync

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

actual class PlatformTaskScheduler(private val context: Context) : BackgroundTaskScheduler {
    actual override fun scheduleSync() {
        val constraints = androidx.work.Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork("SyncWork", ExistingPeriodicWorkPolicy.REPLACE, workRequest)
    }
}