package com.sorabh.node.screens.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.node.database.TaskEntity
import com.sorabh.node.database.TaskRepository
import com.sorabh.node.utils.TaskDateRange
import com.sorabh.node.utils.TaskPriority
import com.sorabh.node.utils.TaskStatus
import com.sorabh.node.utils.TaskType
import com.sorabh.node.utils.currentLocalDateTime
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.plus

class TodayTaskViewModel(private val repository: TaskRepository) : ViewModel() {
    val todayDate = currentLocalDateTime().date
    val startOfDay = LocalDateTime(todayDate, LocalTime(0, 0, 0))

    val tomorrowDate = todayDate.plus(DatePeriod(days = 1))
    val startOfNextDay = LocalDateTime(tomorrowDate, LocalTime(0, 0, 0))

    val todayTasks = repository.getTodayTasks(startOfDay, startOfNextDay)

    val selectedStatus = mutableStateListOf<TaskStatus>()
    val selectedPriority = mutableStateListOf<TaskPriority>()
    val selectedCategory = mutableStateListOf<TaskType>()

    val selectedDataRange = mutableStateOf(TaskDateRange.ALL)

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }

    fun onStatusChanged(taskStatus: TaskStatus) {
        if (taskStatus in selectedStatus)
            selectedStatus.remove(taskStatus)
        else
            selectedStatus.add(taskStatus)
    }

    fun onPriorityChanged(taskPriority: TaskPriority) {
        if (taskPriority in selectedPriority)
            selectedPriority.remove(taskPriority)
        else
            selectedPriority.add(taskPriority)
    }

    fun onCategoryChanged(taskType: TaskType) {
        if (taskType in selectedCategory)
            selectedCategory.remove(taskType)
        else
            selectedCategory.add(taskType)
    }

    fun onTaskDateRangeChanged(dateRange: TaskDateRange) {
        selectedDataRange.value = dateRange
    }

}