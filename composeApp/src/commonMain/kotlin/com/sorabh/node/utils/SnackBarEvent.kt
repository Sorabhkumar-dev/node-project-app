package com.sorabh.node.utils

sealed interface SnackBarEvent

data class ShowSnackBarEvent(val message: String) : SnackBarEvent

data object DismissSnackBarEvent : SnackBarEvent