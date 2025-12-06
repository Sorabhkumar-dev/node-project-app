package com.sorabh.node.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sorabh.node.utils.RepeatType
import com.sorabh.node.utils.TaskType
import kotlinx.datetime.LocalDateTime

@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String = "",
    val dateTime: LocalDateTime,
    val isImportant: Boolean = false,
    val taskType: TaskType = TaskType.WORK,
    val isRepeatable: Boolean = false,
    val repeatType: RepeatType? = null
)