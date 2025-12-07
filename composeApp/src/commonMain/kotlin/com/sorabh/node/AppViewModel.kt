package com.sorabh.node

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Article
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Today
import androidx.lifecycle.ViewModel
import com.sorabh.node.pojo.AppBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {
    val _appBarState = MutableStateFlow<AppBar?>(null)
    val appBarState = _appBarState.asStateFlow()

    val bottomBar = listOf(
        Icons.Rounded.Today,
        Icons.Rounded.Star,
        Icons.AutoMirrored.Rounded.Article,
        Icons.Rounded.Autorenew
    )

    fun onAppBarStateChanged(appBar: AppBar) {
        _appBarState.value = appBar
    }
}