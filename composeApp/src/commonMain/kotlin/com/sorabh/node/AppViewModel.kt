package com.sorabh.node

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.node.datastore.NodePreference
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

class AppViewModel(private val preference: NodePreference) : ViewModel() {

    val appBarState = mutableStateOf<AppBar?>(null)

    private val _topBarEvent = MutableSharedFlow<TopBarEvent>()
    val topBarEvent = _topBarEvent.asSharedFlow()

    private val _snackBarEvent = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    val readTheme = preference.readTheme

    val bottomBar = listOf(
        TodayTaskNav,
        ImportantTaskNav,
        AllTaskNav,
        RepeatTaskNav
    )

    fun onAppBarChanged(appBar: AppBar) {
        appBarState.value = appBar
    }

    fun sendEvent(event: TopBarEvent) {
        viewModelScope.launch { _topBarEvent.emit(event) }
    }

    fun sendEvent(event: SnackBarEvent) {
        viewModelScope.launch { _snackBarEvent.emit(event) }
    }

    fun writeTheme(isLight: Boolean) {
        viewModelScope.launch {
            preference.writeTheme(isLight)
        }
    }
}