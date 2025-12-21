package com.sorabh.node.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Pending
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.ui.graphics.Color

enum class TaskStatus {
    TODO,
    PROGRESS,
    DONE,
    CANCELLED
}

// Extension property to get color based on status
val TaskStatus.color: Color
    get() = when (this) {
        TaskStatus.TODO -> Color(0xFF78909C)   // Blue Grey (Neutral)
        TaskStatus.PROGRESS -> Color(0xFF42A5F5)  // Blue (Active)
        TaskStatus.DONE -> Color(0xFF66BB6A)      // Green (Success)
        TaskStatus.CANCELLED -> Color(0xFFEF5350) // Red (Destructive)
    }

val TaskStatus.icon
    get() = when (this) {
        TaskStatus.TODO -> Icons.Outlined.Pending
        TaskStatus.PROGRESS -> Icons.Outlined.Replay
        TaskStatus.DONE -> Icons.Filled.Done
        TaskStatus.CANCELLED -> Icons.Filled.Cancel
    }