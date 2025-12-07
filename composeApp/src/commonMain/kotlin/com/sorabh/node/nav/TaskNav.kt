package com.sorabh.node.nav

import androidx.navigation3.runtime.NavKey as LibNavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavKey : LibNavKey

@Serializable
sealed interface BottomBarHider

@Serializable
data object AllTaskNav : NavKey

@Serializable
data object TodayTaskNav : NavKey

@Serializable
data object RepeatTaskNav : NavKey

@Serializable
data object AddTaskNav : NavKey, BottomBarHider

@Serializable
data object ImportantTaskNav : NavKey