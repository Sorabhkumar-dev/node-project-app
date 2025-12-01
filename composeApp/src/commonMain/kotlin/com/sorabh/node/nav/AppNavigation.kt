package com.sorabh.node.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sorabh.node.screens.ui.AddTaskScreen
import com.sorabh.node.screens.ui.AllTaskScreen
import com.sorabh.node.screens.ui.ImportantTaskScreen
import com.sorabh.node.screens.ui.RepeatTaskScreen
import com.sorabh.node.screens.ui.TodayTaskScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavigation(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = TodayTaskNav,
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
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
            AddTaskScreen(viewModel = koinViewModel())
        }

        composable<ImportantTaskNav> {
            ImportantTaskScreen()
        }

    }
}