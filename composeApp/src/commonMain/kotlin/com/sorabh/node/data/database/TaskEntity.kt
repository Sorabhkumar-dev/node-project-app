package com.sorabh.node.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sorabh.node.ui.utils.RepeatType
import com.sorabh.node.ui.utils.TaskPriority
import com.sorabh.node.ui.utils.TaskStatus
import com.sorabh.node.ui.utils.TaskCategory
import com.sorabh.node.ui.utils.currentLocalDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String = "",
    val isRepeatable: Boolean = false,
    val isSynced: Boolean = false,
    val markAsDelete: Boolean = false,
    val repeatType: String? = RepeatType.DAILY.name,
    val priority: String = TaskPriority.LOW.name,
    val taskStatus: String = TaskStatus.TODO.name,
    val taskCategory: String = TaskCategory.OTHER.name,
    val dateTime: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime = currentLocalDateTime()
)