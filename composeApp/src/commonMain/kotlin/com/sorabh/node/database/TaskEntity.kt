package com.sorabh.node.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sorabh.node.utils.RepeatType
import com.sorabh.node.utils.TaskPriority
import com.sorabh.node.utils.TaskStatus
import com.sorabh.node.utils.TaskCategory
import com.sorabh.node.utils.currentLocalDateTime
import kotlinx.datetime.LocalDateTime

@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val email: String,
    val title: String,
    val description: String = "",
    val isRepeatable: Boolean = false,
    val isSynced: Boolean = false,
    val markAsDelete: Boolean = false,
    val repeatType: RepeatType? = null,
    val priority: TaskPriority = TaskPriority.LOW,
    val taskStatus: TaskStatus = TaskStatus.TODO,
    val taskCategory: TaskCategory = TaskCategory.OTHER,
    val dateTime: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime = currentLocalDateTime()
)