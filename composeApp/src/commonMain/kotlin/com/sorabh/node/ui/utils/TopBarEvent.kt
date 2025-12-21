package com.sorabh.node.ui.utils


sealed interface TopBarEvent

data object AddTaskEvent : TopBarEvent

data class FilterTaskEvent(val isFilterApplied: Boolean) : TopBarEvent