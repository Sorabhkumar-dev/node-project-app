package com.sorabh.node.utils

import androidx.navigation3.runtime.NavKey


sealed interface TopBarEvent

data class NavigateEvent(val route: NavKey) : TopBarEvent

data object AddTaskEvent : TopBarEvent