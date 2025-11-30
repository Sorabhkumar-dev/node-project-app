package com.sorabh.node.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sorabh.node.screens.ui.AddTaskScreen
import com.sorabh.node.screens.ui.AllTaskScreen
import com.sorabh.node.screens.ui.ImportantTaskScreen
import com.sorabh.node.screens.ui.RepeatTaskScreen
import com.sorabh.node.screens.ui.TodayTaskScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, TodayTaskNav) {
        composable<TodayTaskNav> {
            TodayTaskScreen()
        }

        composable<AllTaskNav> {
            AllTaskScreen()
        }

        composable<RepeatTaskNav> {
            RepeatTaskScreen()
        }

        composable<AddTaskNav> {
            AddTaskScreen()
        }

        composable<ImportantTaskNav> {
            ImportantTaskScreen()
        }

    }
}