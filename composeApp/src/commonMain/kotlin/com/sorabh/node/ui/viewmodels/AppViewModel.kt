package com.sorabh.node.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.node.data.datastore.NodePreference
import com.sorabh.node.ui.pojo.AppBar
import com.sorabh.node.ui.nav.AllTaskNav
import com.sorabh.node.ui.nav.ImportantTaskNav
import com.sorabh.node.ui.nav.RepeatTaskNav
import com.sorabh.node.ui.nav.TodayTaskNav
import com.sorabh.node.ui.utils.SnackBarEvent
import com.sorabh.node.ui.utils.TopBarEvent
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