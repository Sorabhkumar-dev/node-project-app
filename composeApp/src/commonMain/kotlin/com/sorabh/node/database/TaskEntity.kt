package com.sorabh.node.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sorabh.node.utils.RepeatType
import com.sorabh.node.utils.TaskStatus
import com.sorabh.node.utils.TaskType
import com.sorabh.node.utils.currentLocalDateTime
import kotlinx.datetime.LocalDateTime

@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val email: String,
    val title: String,
    val description: String = "",
    val isImportant: Boolean = false,
    val isRepeatable: Boolean = false,
    val isSynced: Boolean = false,
    val markAsDelete: Boolean = false,
    val repeatType: RepeatType? = null,
    val taskStatus: TaskStatus = TaskStatus.TODO,
    val taskType: TaskType = TaskType.OTHER,
    val dateTime: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime = currentLocalDateTime()
)