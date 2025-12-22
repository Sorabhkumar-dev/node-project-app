package com.sorabh.node.data.remote.pojo.shared

import com.sorabh.node.ui.utils.RepeatType
import com.sorabh.node.ui.utils.TaskCategory
import com.sorabh.node.ui.utils.TaskPriority
import com.sorabh.node.ui.utils.TaskStatus
import com.sorabh.node.ui.utils.currentLocalDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class TaskDTO(
    val id: Long,
    val title: String,
    val description: String = "",
    val isRepeatable: Boolean = false,
    val isSynced: Boolean = false,
    val markAsDelete: Boolean = false,
    val repeatType: RepeatType? = RepeatType.DAILY,
    val priority: TaskPriority = TaskPriority.LOW,
    val taskStatus: TaskStatus = TaskStatus.TODO,
    val taskCategory: TaskCategory = TaskCategory.OTHER,
    val dateTime: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime = currentLocalDateTime()
)