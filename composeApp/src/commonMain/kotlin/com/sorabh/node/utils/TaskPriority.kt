package com.sorabh.node.utils

import androidx.compose.ui.graphics.Color

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