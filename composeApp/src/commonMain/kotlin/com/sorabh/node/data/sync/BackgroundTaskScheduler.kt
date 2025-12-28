package com.sorabh.node.data.sync

interface BackgroundTaskScheduler {
    fun scheduleSync()
}

expect class PlatformTaskScheduler : BackgroundTaskScheduler {
    override fun scheduleSync()
}
