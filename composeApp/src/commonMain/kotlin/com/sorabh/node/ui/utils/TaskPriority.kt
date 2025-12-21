package com.sorabh.node.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class TaskPriority {
    LOW,
    MEDIUM,
    HIGH
}


val TaskPriority.color: Color
    get() = when (this) {
        TaskPriority.LOW -> Color(0xFF34C759)     // iOS green – visible
        TaskPriority.MEDIUM -> Color(0xFFFF9500) // Orange – much better than yellow
        TaskPriority.HIGH -> Color(0xFFFF3B30)   // iOS red – strong contrast
    }

val TaskPriority.icon: ImageVector
    get() = when (this) {
        TaskPriority.LOW -> Icons.Outlined.NotificationsNone
        TaskPriority.MEDIUM -> Icons.Outlined.Notifications
        TaskPriority.HIGH -> Icons.Filled.NotificationImportant
    }