package com.sorabh.node.screens.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TodayTaskScreen() {
    TodayTaskContent()
}

@Composable
private fun TodayTaskContent() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {

        }
    }
}