package com.sorabh.node.nav

import com.sorabh.node.utils.TaskPriority
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavKey

@Serializable
sealed interface BottomBarHider

@Serializable
sealed interface BottomBar

@Serializable
data object AllTaskNav : NavKey, BottomBar

@Serializable
data object TodayTaskNav : NavKey, BottomBar

@Serializable
data object RepeatTaskNav : NavKey, BottomBar

@Serializable
data class AddTaskNav(val taskId: Long? = null,val priority: TaskPriority = TaskPriority.MEDIUM, val isRepeatable: Boolean = false) : NavKey, BottomBarHider

@Serializable
data object ImportantTaskNav : NavKey, BottomBar

@Serializable
data class TaskDetailNav(val id: Long) : NavKey, BottomBarHider