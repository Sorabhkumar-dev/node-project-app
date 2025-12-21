package com.sorabh.node.ui.utils

import androidx.compose.ui.graphics.vector.ImageVector

sealed interface SnackBarEvent

data class ShowSnackBarEvent(val message: String, val icon: ImageVector) : SnackBarEvent

data object DismissSnackBarEvent : SnackBarEvent