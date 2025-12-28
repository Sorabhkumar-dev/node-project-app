package com.sorabh.node.data.sync

import kotlinx.cinterop.ExperimentalForeignApi
import platform.BackgroundTasks.*
import platform.Foundation.*

actual class PlatformTaskScheduler : BackgroundTaskScheduler {
    private val taskId = "com.sorabh.node.syncTask"

    fun register() {
        BGTaskScheduler.sharedScheduler.registerForTaskWithIdentifier(
            taskId,
            null
        ) { task ->
            handleTask(task as BGAppRefreshTask)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual override fun scheduleSync() {
        val request = BGAppRefreshTaskRequest(taskId)
        request.earliestBeginDate = NSDate().dateByAddingTimeInterval(3600.0) // 1 hour

        val submitted = BGTaskScheduler.sharedScheduler.submitTaskRequest(request, null)
        if (!submitted) {
            println("Could not schedule bg task")
        }
    }

    private fun handleTask(task: BGAppRefreshTask) {
        scheduleSync()

        task.expirationHandler = {
            // Cancel any ongoing work
        }

        // Perform your background work here
        // e.g., Repository.sync()

        task.setTaskCompletedWithSuccess(true)
    }
}