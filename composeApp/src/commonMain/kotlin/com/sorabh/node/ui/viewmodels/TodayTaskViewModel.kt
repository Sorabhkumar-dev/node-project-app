package com.sorabh.node.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.node.data.database.TaskEntity
import com.sorabh.node.data.database.TaskRepository
import com.sorabh.node.data.remote.ApiRepository
import com.sorabh.node.ui.utils.TaskCategory
import com.sorabh.node.ui.utils.TaskDateRange
import com.sorabh.node.ui.utils.TaskPriority
import com.sorabh.node.ui.utils.TaskStatus
import com.sorabh.node.ui.utils.currentLocalDateTime
import com.sorabh.node.ui.utils.toDateTimeRange
import com.sorabh.node.ui.utils.toLocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.plus

class TodayTaskViewModel(private val repository: TaskRepository,private val apiRepository: ApiRepository) : ViewModel() {
    private val todayDate = currentLocalDateTime().date
    var startOfDay = mutableStateOf(LocalDateTime(todayDate, LocalTime(0, 0, 0)))

    private val tomorrowDate = todayDate.plus(DatePeriod(days = 1))
    var startOfNextDay = mutableStateOf(LocalDateTime(tomorrowDate, LocalTime(0, 0, 0)))

    private val _todayTasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val todayTasks = _todayTasks.asStateFlow()

    val selectedStatus = mutableStateListOf<TaskStatus>()
    val selectedPriority = mutableStateListOf<TaskPriority>()
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

        // Reset date range selection
        selectedDataRange.value = null

        // Reset dates to today range
        val today = currentLocalDateTime().date
        val tomorrow = today.plus(DatePeriod(days = 1))

        startOfDay.value = LocalDateTime(today, LocalTime(0, 0, 0))
        startOfNextDay.value = LocalDateTime(tomorrow, LocalTime(0, 0, 0))

        // Optional: close date picker
        showDateRangePickerState.value = false

        // Refresh task list
        getTodayTasks()
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
                startDateTime = if (selectedDataRange.value == TaskDateRange.CUSTOM_RANGE) startOfDay.value else dateRange?.start
                    ?: startOfDay.value,
                endDateTime = if (selectedDataRange.value == TaskDateRange.CUSTOM_RANGE) startOfNextDay.value else dateRange?.end
                    ?: startOfNextDay.value,
                isRepeatable = null
            ).collect {
                _todayTasks.value = it
            }
        }
    }

   fun syncTasks() {
       viewModelScope.launch {
           apiRepository.syncTasks()
       }
   }


}