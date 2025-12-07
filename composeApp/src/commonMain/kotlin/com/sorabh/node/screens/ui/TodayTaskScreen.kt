package com.sorabh.node.screens.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.sorabh.node.AppViewModel
import com.sorabh.node.pojo.AppBar
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.today_task

@Composable
fun TodayTaskScreen(sharedViewModel: AppViewModel) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        sharedViewModel.onAppBarStateChanged(
            AppBar(
                title = Res.string.today_task,
                icon = Icons.Default.Add
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