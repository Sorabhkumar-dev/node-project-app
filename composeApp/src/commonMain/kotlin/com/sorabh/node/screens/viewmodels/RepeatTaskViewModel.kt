package com.sorabh.node.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.node.database.TaskEntity
import com.sorabh.node.database.TaskRepository
import kotlinx.coroutines.launch

class RepeatTaskViewModel(private val repository: TaskRepository): ViewModel() {
    val repeatingTasks = repository.getRepeatingTasks()

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: TaskEntity){
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}