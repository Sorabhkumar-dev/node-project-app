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
import com.sorabh.node.utils.TaskCategory
import com.sorabh.node.utils.currentLocalDateTime
import com.sorabh.node.utils.toDateTimeRange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.plus

class TodayTaskViewModel(private val repository: TaskRepository) : ViewModel() {
    private val todayDate = currentLocalDateTime().date
    private val startOfDay = LocalDateTime(todayDate, LocalTime(0, 0, 0))

    private val tomorrowDate = todayDate.plus(DatePeriod(days = 1))
    private val startOfNextDay = LocalDateTime(tomorrowDate, LocalTime(0, 0, 0))

    val _todayTasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val todayTasks = _todayTasks.asStateFlow()

    val selectedStatus = mutableStateListOf<TaskStatus>()
    val selectedPriority = mutableStateListOf<TaskPriority>()
    val selectedCategory = mutableStateListOf<TaskCategory>()

    val selectedDataRange = mutableStateOf<TaskDateRange?>(null)

    init {
        getTodayTasks()
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

    fun onCategoryChanged(taskCategory: TaskCategory) {
        if (taskCategory in selectedCategory)
            selectedCategory.remove(taskCategory)
        else
            selectedCategory.add(taskCategory)
    }

    fun onTaskDateRangeChanged(dateRange: TaskDateRange) {
        selectedDataRange.value = dateRange
    }

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

    fun getTodayTasks() {
        viewModelScope.launch {
            val dateRange = selectedDataRange.value?.toDateTimeRange()
            repository.getFilteredTasks(
                statuses = selectedStatus,
                filterStatus = selectedStatus.isNotEmpty(),
                types = selectedCategory,
                filterType = selectedCategory.isNotEmpty(),
                priorities = selectedPriority,
                filterPriority = selectedPriority.isNotEmpty(),
                startDateTime = dateRange?.start ?: startOfDay,
                endDateTime = dateRange?.end ?: startOfNextDay
            ).collect {
                _todayTasks.value = it
            }
        }
    }

}