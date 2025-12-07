package com.sorabh.node.utils

import androidx.compose.ui.graphics.Color

enum class TaskStatus {
    PENDING,
    PROGRESS,
    DONE,
    CANCELLED
}

// Extension property to get color based on status
val TaskStatus.color: Color
    get() = when (this) {
        TaskStatus.PENDING -> Color(0xFF78909C)   // Blue Grey (Neutral)
        TaskStatus.PROGRESS -> Color(0xFF42A5F5)  // Blue (Active)
        TaskStatus.DONE -> Color(0xFF66BB6A)      // Green (Success)
        TaskStatus.CANCELLED -> Color(0xFFEF5350) // Red (Destructive)
    }

// Optional: A softer background color for Chips/Badges
val TaskStatus.containerColor: Color
    get() = when (this) {
        TaskStatus.PENDING -> Color(0xFFECEFF1)
        TaskStatus.PROGRESS -> Color(0xFFE3F2FD)
        TaskStatus.DONE -> Color(0xFFE8F5E9)
        TaskStatus.CANCELLED -> Color(0xFFFFEBEE)
    }