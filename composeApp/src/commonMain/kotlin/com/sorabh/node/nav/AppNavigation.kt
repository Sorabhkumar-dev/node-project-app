package com.sorabh.node.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.sorabh.node.AppViewModel
import com.sorabh.node.screens.ui.AddTaskScreen
import com.sorabh.node.screens.ui.AllTaskScreen
import com.sorabh.node.screens.ui.ImportantTaskScreen
import com.sorabh.node.screens.ui.RepeatTaskScreen
import com.sorabh.node.screens.ui.TodayTaskScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavigation(
    viewModel: AppViewModel,
    navBackStack: NavBackStack<NavKey>,
    paddingValues: PaddingValues
) {
    val onNavigate: (NavKey) -> Unit = {
        navBackStack.add(it)
    }

    NavDisplay(
        backStack = navBackStack,
        modifier = Modifier.padding(paddingValues)
    ) { key ->
        when (key) {
            is TodayTaskNav -> NavEntry(key) {
                TodayTaskScreen(
                    sharedViewModel = viewModel,
                    viewModel = koinViewModel(),
                    onNavigate = onNavigate
                )
            }

            is AllTaskNav -> NavEntry(key) {
                AllTaskScreen()
            }

            is RepeatTaskNav -> NavEntry(key) {
                RepeatTaskScreen()
            }

            is AddTaskNav -> NavEntry(key) {
                AddTaskScreen(
                    viewModel = koinViewModel(),
                    sharedViewModel = viewModel,
                    sendSnackBarEvent = viewModel::sendEvent,
                    sendTopBarEvent = viewModel::onAppBarStateChanged
                )
            }

            is ImportantTaskNav -> NavEntry(key) {
                ImportantTaskScreen()
            }

            else -> throw Exception("Unknown route")
        }
    }
}