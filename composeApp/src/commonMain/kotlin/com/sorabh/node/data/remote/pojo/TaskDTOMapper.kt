package com.sorabh.node.data.remote.pojo

import com.sorabh.node.data.database.TaskEntity
import com.sorabh.node.data.remote.pojo.shared.TaskDTO

fun TaskDTO.toTaskEntity() =
    TaskEntity(
        id = id,
        title = title,
        description = description,
        isRepeatable = isRepeatable,
        isSynced = isSynced,
        markAsDelete = markAsDelete,
        repeatType = repeatType,
        priority = priority,
        taskStatus = taskStatus,
        taskCategory = taskCategory,
        dateTime = dateTime,
        createdAt = createdAt,
    )

fun TaskEntity.toTaskDTO() =
    TaskDTO(
        id = id,
        title = title,
        description = description,
        isRepeatable = isRepeatable,
        isSynced = isSynced,
        markAsDelete = markAsDelete,
        repeatType = repeatType,
        priority = priority,
        taskStatus = taskStatus,
        taskCategory = taskCategory,
        dateTime = dateTime,
        createdAt = createdAt,
    )