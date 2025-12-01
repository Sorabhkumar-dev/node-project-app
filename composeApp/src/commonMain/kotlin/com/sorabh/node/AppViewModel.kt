package com.sorabh.node

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Today
import androidx.lifecycle.ViewModel
import com.sorabh.node.pojo.AppBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {
    val _appBarState = MutableStateFlow<AppBar?>(null)
    val appBarState = _appBarState.asStateFlow()

    val bottomBar = listOf(
        Icons.Default.Today,
        Icons.Default.NotificationImportant,
        Icons.Default.Task,
        Icons.Default.Replay
    )

    fun onAppBarStateChanged(appBar: AppBar) {
        _appBarState.value = appBar
    }
}