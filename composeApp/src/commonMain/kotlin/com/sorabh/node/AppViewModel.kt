package com.sorabh.node

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Article
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Today
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavKey
import com.sorabh.node.nav.AllTaskNav
import com.sorabh.node.nav.ImportantTaskNav
import com.sorabh.node.nav.RepeatTaskNav
import com.sorabh.node.nav.TodayTaskNav
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

    val bottomBar = mapOf<ImageVector, NavKey>(
        Icons.Rounded.Today to TodayTaskNav,
        Icons.Rounded.Star to ImportantTaskNav,
        Icons.AutoMirrored.Rounded.Article to AllTaskNav,
        Icons.Rounded.Repeat to RepeatTaskNav
    )

    fun sendEvent(event: TopBarEvent) {
        viewModelScope.launch { _topBarEvent.emit(event) }
    }

    fun sendEvent(event: SnackBarEvent) {
        viewModelScope.launch { _snackBarEvent.emit(event) }
    }

    fun onAppBarChanged(appBar: AppBar) {
        appBarState.value = appBar
    }
}