package com.sorabh.node.screens.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.sorabh.node.AppViewModel
import com.sorabh.node.nav.AddTaskNav
import com.sorabh.node.pojo.AppBar
import com.sorabh.node.screens.viewmodels.TodayTaskViewModel
import com.sorabh.node.utils.NavigateEvent
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.today_task

@Composable
fun TodayTaskScreen(
    sharedViewModel: AppViewModel,
    viewModel: TodayTaskViewModel,
    navBackStack: NavBackStack<NavKey>
) {
    LaunchedEffect(Unit) {
        sharedViewModel.topBarEvent.collect {
            if (it is NavigateEvent)
                navBackStack.add(it.route)
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        sharedViewModel.onAppBarStateChanged(
            AppBar(
                title = Res.string.today_task,
                icon = Icons.Default.Add,
                event = NavigateEvent(AddTaskNav)
            )
        )
    }
    TodayTaskContent()
}

@Composable
private fun TodayTaskContent() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {

        }
    }
}