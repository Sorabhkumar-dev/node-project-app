package com.sorabh.node.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sorabh.node.AppViewModel
import com.sorabh.node.screens.ui.AddTaskScreen
import com.sorabh.node.screens.ui.AllTaskScreen
import com.sorabh.node.screens.ui.ImportantTaskScreen
import com.sorabh.node.screens.ui.RepeatTaskScreen
import com.sorabh.node.screens.ui.TaskDetailScreen
import com.sorabh.node.screens.ui.TodayTaskScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigation(
    viewModel: AppViewModel,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    val onNavigate: (NavKey) -> Unit = {
        navController.navigate(it)
    }


    NavHost(
        navController = navController,
        modifier = Modifier.padding(paddingValues),
        startDestination = TodayTaskNav,

        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        }
    ) {
        composable<TodayTaskNav> {
            TodayTaskScreen(
                sharedViewModel = viewModel,
                onAppBarChanged = viewModel::onAppBarChanged,
                onNavigate = onNavigate
            )
        }

        composable<AllTaskNav> {
            AllTaskScreen(
                sharedViewModel = viewModel,
                onAppBarChanged = viewModel::onAppBarChanged,
                onNavigate = onNavigate
            )
        }

        composable<RepeatTaskNav> {
            RepeatTaskScreen(
                sharedViewModel = viewModel,
                onNavigate = onNavigate,
                onAppBarChanged = viewModel::onAppBarChanged
            )
        }

        composable<AddTaskNav> {
            AddTaskScreen(
                viewModel = koinViewModel{ parametersOf(it.toRoute<AddTaskNav>()) },
                sharedViewModel = viewModel,
                sendSnackBarEvent = viewModel::sendEvent,
                sendTopBarEvent = viewModel::onAppBarChanged
            )
        }

        composable<ImportantTaskNav> {
            ImportantTaskScreen(
                sharedViewModel = viewModel,
                onAppBarChanged = viewModel::onAppBarChanged,
                onNavigate = onNavigate
            )
        }

        composable<TaskDetailNav> {
            TaskDetailScreen(
                viewModel = koinViewModel { parametersOf(it.toRoute<TaskDetailNav>()) },
                sendTopBarEvent = viewModel::onAppBarChanged
            )
        }
    }

}