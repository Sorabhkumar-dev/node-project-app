package com.sorabh.node.utils

import com.sorabh.node.nav.NavKey


sealed interface TopBarEvent

data class NavigateEvent(val route: NavKey) : TopBarEvent

data object AddTaskEvent : TopBarEvent