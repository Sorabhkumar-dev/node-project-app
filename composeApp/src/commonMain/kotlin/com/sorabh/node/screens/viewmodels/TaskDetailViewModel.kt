package com.sorabh.node.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.node.database.TaskEntity
import com.sorabh.node.database.TaskRepository
import com.sorabh.node.nav.TaskDetailNav
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskDetailViewModel(private val repository: TaskRepository, val navData: TaskDetailNav) :
    ViewModel() {

    private val _taskDetailFlow = MutableStateFlow<TaskEntity?>(null)
    val taskDetailFlow = _taskDetailFlow.asStateFlow()

    fun getTaskDetail() {
        viewModelScope.launch {
            repository.getTask(navData.id).collect {
                _taskDetailFlow.value = it
            }
        }
    }

}