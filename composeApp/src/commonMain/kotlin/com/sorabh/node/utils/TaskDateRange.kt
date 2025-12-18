package com.sorabh.node.utils

enum class TaskDateRange(val value: String) {
    ALL("All"),
    TODAY("Today"),
    THIS_WEEK("This Week"),
    LAST_SEVEN_DAY("Last 7 days"),
    LAST_THIRTY_DAY("Last 30 days"),
    THIS_MONTH("This Month"),
    LAST_MONTH("Last Month"),
    CUSTOM_RANGE("Custom Range")
}