package com.sorabh.node.screens.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.node.database.TaskEntity
import com.sorabh.node.database.TaskRepository
import com.sorabh.node.utils.TaskCategory
import com.sorabh.node.utils.TaskDateRange
import com.sorabh.node.utils.TaskPriority
import com.sorabh.node.utils.TaskStatus
import com.sorabh.node.utils.currentLocalDateTime
import com.sorabh.node.utils.toDateTimeRange
import com.sorabh.node.utils.toLocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.plus

class ImportantTaskViewModel(private val repository: TaskRepository) : ViewModel() {

    var startOfDay = mutableStateOf<LocalDateTime?>(null)
    var startOfNextDay = mutableStateOf<LocalDateTime?>(null)

    private val _importantTasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val importantTasks = _importantTasks.asStateFlow()

    val selectedStatus = mutableStateListOf<TaskStatus>()
    val selectedPriority = mutableStateListOf(TaskPriority.HIGH)
    val selectedCategory = mutableStateListOf<TaskCategory>()

    val selectedDataRange = mutableStateOf<TaskDateRange?>(null)

    val showDateRangePickerState = mutableStateOf(false)

    val isAnyFilterApplied: Boolean
        get() {
            val isStatusFilter = selectedStatus.isNotEmpty()
            val isPriorityFilter = selectedPriority.isNotEmpty()
            val isCategoryFilter = selectedCategory.isNotEmpty()

            val isDateFilter = when (selectedDataRange.value) {
                null -> false
                TaskDateRange.CUSTOM_RANGE -> {
                    val today = currentLocalDateTime().date
                    val defaultStart = LocalDateTime(today, LocalTime(0, 0, 0))
                    val defaultEnd = LocalDateTime(today.plus(DatePeriod(days = 1)), LocalTime(0, 0, 0))

                    startOfDay.value != defaultStart ||
                            startOfNextDay.value != defaultEnd
                }
                else -> true
            }

            return isStatusFilter ||
                    isPriorityFilter ||
                    isCategoryFilter ||
                    isDateFilter
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

    fun onDateRangePickerChanged(isShow: Boolean = false) {
        showDateRangePickerState.value = isShow
    }

    fun onDateRangeSelected(pair: Pair<Long?, Long?>) {
        if (pair.first != null && pair.second != null) {
            startOfDay.value =
                LocalDateTime(pair.first!!.toLocalDate(), LocalTime(23, 59, 59, 999_999_999))
            startOfNextDay.value =
                LocalDateTime(pair.second!!.toLocalDate(), LocalTime(23, 59, 59, 999_999_999))
        }
    }

    fun resetFilters() {
        // Clear multi-select filters
        selectedStatus.clear()
        selectedPriority.clear()
        selectedCategory.clear()

        selectedPriority.add(TaskPriority.HIGH)

        // Reset date range selection
        selectedDataRange.value = null

        startOfDay.value = null
        startOfNextDay.value = null

        // Optional: close date picker
        showDateRangePickerState.value = false

        // Refresh task list
        getImportantTasks()
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

    fun getImportantTasks() {
        viewModelScope.launch {
            val dateRange = selectedDataRange.value?.toDateTimeRange()
            repository.getFilteredTasks(
                statuses = selectedStatus,
                filterStatus = selectedStatus.isNotEmpty(),
                types = selectedCategory,
                filterType = selectedCategory.isNotEmpty(),
                priorities = selectedPriority,
                filterPriority = selectedPriority.isNotEmpty(),
                startDateTime = if (selectedDataRange.value == TaskDateRange.CUSTOM_RANGE) startOfDay.value else dateRange?.start
                    ?: startOfDay.value,
                endDateTime = if (selectedDataRange.value == TaskDateRange.CUSTOM_RANGE) startOfNextDay.value else dateRange?.end
                    ?: startOfNextDay.value
            ).collect {
                _importantTasks.value = it
            }
        }
    }
}