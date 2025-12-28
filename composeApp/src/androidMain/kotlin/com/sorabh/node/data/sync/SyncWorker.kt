package com.sorabh.node.data.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sorabh.node.data.remote.ApiRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val repository: ApiRepository by inject()

    override suspend fun doWork(): Result {
        return try {
            repository.syncTasks()
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}