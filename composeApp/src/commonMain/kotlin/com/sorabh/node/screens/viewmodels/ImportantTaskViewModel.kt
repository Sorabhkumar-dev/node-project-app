package com.sorabh.node.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.node.database.TaskEntity
import com.sorabh.node.database.TaskRepository
import kotlinx.coroutines.launch

class ImportantTaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val importantTasks = repository.getImportantTasks()

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}