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
import com.sorabh.node.AppViewModel
import com.sorabh.node.components.EmptyTaskState
import com.sorabh.node.components.SwipeableTaskCard
import com.sorabh.node.components.TaskCard
import com.sorabh.node.nav.AddTaskNav
import com.sorabh.node.nav.NavKey
import com.sorabh.node.pojo.AppBar
import com.sorabh.node.screens.viewmodels.RepeatTaskViewModel
import com.sorabh.node.utils.NavigateEvent
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.repeating_task
import node.composeapp.generated.resources.repeating_tasks
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RepeatTaskScreen(
    sharedViewModel: AppViewModel,
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
                title = Res.string.repeating_task,
                icon = Icons.Default.Add,
                event = NavigateEvent(AddTaskNav(isRepeatable = true))
            )
        )
    }

    RepeatTaskContent(onNavigate = onNavigate)
}

@Composable
private fun RepeatTaskContent(onNavigate: (NavKey) -> Unit) {
    val viewModel = koinViewModel<RepeatTaskViewModel>()
    val repeatingTasks = viewModel.repeatingTasks.collectAsState(emptyList()).value

    if (repeatingTasks.isEmpty())
        EmptyTaskState(image = Res.drawable.repeating_tasks) {
            onNavigate(AddTaskNav(isRepeatable = true))
        }
    else
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(repeatingTasks, key = { it.id }) {
                SwipeableTaskCard(
                    task = it,
                    onDelete = viewModel::deleteTask,
                    onComplete = viewModel::updateTask
                ) { task ->
                    TaskCard(task = task, onClick = onNavigate)
                }
            }
        }
}