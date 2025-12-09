package com.sorabh.node.screens.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation3.runtime.NavKey
import com.sorabh.node.AppViewModel
import com.sorabh.node.components.EmptyTaskState
import com.sorabh.node.components.SwipeableTaskCard
import com.sorabh.node.components.TaskCard
import com.sorabh.node.nav.AddTaskNav
import com.sorabh.node.pojo.AppBar
import com.sorabh.node.screens.viewmodels.AllTaskViewModel
import com.sorabh.node.utils.NavigateEvent
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.all_task

@Composable
fun AllTaskScreen(
    sharedViewModel: AppViewModel,
    viewModel: AllTaskViewModel,
    onAppBarChanged: (AppBar) -> Unit,
    onNavigate: (NavKey) -> Unit,
) {
    LaunchedEffect(Unit) {
        sharedViewModel.topBarEvent.collect {
            if (it is NavigateEvent)
                onNavigate(it.route)
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onAppBarChanged(
            AppBar(
                title = Res.string.all_task,
                icon = Icons.Default.Add,
                event = NavigateEvent(AddTaskNav)
            )
        )
    }
    AllTaskContent(viewModel = viewModel, onNavigate = onNavigate)
}

@Composable
private fun AllTaskContent(viewModel: AllTaskViewModel, onNavigate: (NavKey) -> Unit) {
    val todayTasks = viewModel.allTasks.collectAsState(emptyList()).value

    if (todayTasks.isEmpty())
        EmptyTaskState {
            onNavigate(AddTaskNav)
        }
    else
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(todayTasks, key = { it.id }) {
                SwipeableTaskCard(
                    task = it,
                    onDelete = viewModel::deleteTask,
                    onComplete = viewModel::updateTask
                ) { task ->
                    TaskCard(task = task)
                }
            }
        }
}