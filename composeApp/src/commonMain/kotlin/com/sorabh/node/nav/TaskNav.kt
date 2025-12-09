package com.sorabh.node.nav

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


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
data object AddTaskNav : NavKey, BottomBarHider

@Serializable
data object ImportantTaskNav : NavKey, BottomBar