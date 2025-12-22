package com.sorabh.node.data.remote.pojo.response

import com.sorabh.node.data.remote.pojo.shared.TaskDTO
import kotlinx.serialization.Serializable

@Serializable
data class SyncResponse(
    val serverTasks: List<TaskDTO>,
    val syncedIds: List<Long>
)