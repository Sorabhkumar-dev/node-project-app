package com.sorabh.node.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CalendarViewWeek
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.EventRepeat
import androidx.compose.material.icons.outlined.Today
import androidx.compose.ui.graphics.vector.ImageVector

enum class RepeatType(
    val value: String,
    val icon: ImageVector
) {
    DAILY(
        value = "Daily",
        icon = Icons.Outlined.Today
    ),
    WEEKLY(
        value = "Weekly",
        icon = Icons.Outlined.DateRange
    ),
    BI_WEEKLY(
        value = "Bi-Weekly",
        icon = Icons.Outlined.CalendarViewWeek
    ),
    MONTHLY(
        value = "Monthly",
        icon = Icons.Outlined.CalendarMonth
    ),
    QUARTERLY(
        value = "Quarterly",
        icon = Icons.Outlined.EventRepeat
    ),
    YEARLY(
        value = "Yearly",
        icon = Icons.Outlined.Event
    )
}

fun String.toRepeatType(): RepeatType = when (this) {
    RepeatType.DAILY.name -> RepeatType.DAILY
    RepeatType.WEEKLY.name -> RepeatType.WEEKLY
    RepeatType.BI_WEEKLY.name -> RepeatType.BI_WEEKLY
    RepeatType.MONTHLY.name -> RepeatType.MONTHLY
    RepeatType.QUARTERLY.name -> RepeatType.QUARTERLY
    RepeatType.YEARLY.name -> RepeatType.YEARLY
    else -> throw Exception("Invalid Repeat Type")
}
