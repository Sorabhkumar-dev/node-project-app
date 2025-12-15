package com.sorabh.node.database.type_converters

import androidx.room.TypeConverter
import com.sorabh.node.utils.TaskPriority

class PriorityConverter {

    @TypeConverter
    fun fromPriority(priority: TaskPriority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(value: String): TaskPriority {
        return TaskPriority.valueOf(value)
    }
}