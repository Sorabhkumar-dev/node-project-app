package com.sorabh.node.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.node.database.TaskEntity
import com.sorabh.node.database.TaskRepository
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