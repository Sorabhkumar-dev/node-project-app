package com.sorabh.node.data.remote.pojo.request

import com.sorabh.node.data.remote.pojo.shared.TaskDTO
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SyncRequest(
    val lastSyncedAt: LocalDateTime,
    val clientTasks: List<TaskDTO>
)