package com.sorabh.node

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Article
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Today
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.node.pojo.AppBar
import com.sorabh.node.utils.SnackBarEvent
import com.sorabh.node.utils.TopBarEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {

    private val _topBarEvent = MutableSharedFlow<TopBarEvent>()
    val topBarEvent = _topBarEvent.asSharedFlow()

    private val _snackBarEvent = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    val appBarState = mutableStateOf<AppBar?>(null)

    val bottomBar = listOf(
        Icons.Rounded.Today,
        Icons.Rounded.Star,
        Icons.AutoMirrored.Rounded.Article,
        Icons.Rounded.Repeat
    )

    fun sendEvent(event: TopBarEvent) {
        viewModelScope.launch { _topBarEvent.emit(event) }
    }

    fun sendEvent(event: SnackBarEvent) {
        viewModelScope.launch { _snackBarEvent.emit(event) }
    }

    fun onAppBarStateChanged(appBar: AppBar) {
        appBarState.value = appBar
    }
}