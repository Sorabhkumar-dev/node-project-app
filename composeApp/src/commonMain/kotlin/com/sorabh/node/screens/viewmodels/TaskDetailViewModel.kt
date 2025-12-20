package com.sorabh.node.screens.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.node.database.TaskEntity
import com.sorabh.node.database.TaskRepository
import com.sorabh.node.nav.TaskDetailNav
import com.sorabh.node.utils.RepeatType
import com.sorabh.node.utils.TaskCategory
import com.sorabh.node.utils.TaskPriority
import com.sorabh.node.utils.TaskStatus
import com.sorabh.node.utils.currentLocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class TaskDetailViewModel(private val repository: TaskRepository, val navData: TaskDetailNav) :
    ViewModel() {

    private val _taskDetailFlow = MutableStateFlow<TaskEntity?>(null)
    val taskDetailFlow = _taskDetailFlow.asStateFlow()

    val isTaskRepeatable = mutableStateOf(false)

    val selectedRepeatType = mutableStateOf<RepeatType?>(null)
    val repeatDropDownExpand = mutableStateOf(false)

    val selectedStatus = mutableStateOf<TaskStatus?>(null)
    val statusDropDownExpand = mutableStateOf(false)

    val selectedPriority = mutableStateOf<TaskPriority?>(null)
    val priorityDropDownExpand = mutableStateOf(false)

    val selectedCategory = mutableStateOf<TaskCategory?>(null)
    val categoryDropDownExpand = mutableStateOf(false)


    fun onTaskRepeatableChanged(isRepeatable: Boolean = false) {
        isTaskRepeatable.value = isRepeatable
    }

    fun onRepeatTypeChanged(
        taskId: Long,
        isRepeatable: Boolean,
        repeatType: RepeatType = RepeatType.DAILY
    ) {
        updateTaskPartial(taskId, isRepeatable = isRepeatable, repeatType = repeatType)
    }

    fun onStatusSelected(status: TaskStatus) {
        selectedStatus.value = status
    }

    fun onStatusChanged(taskId: Long, status: TaskStatus) {
        updateTaskPartial(taskId, taskStatus = status)
    }

    fun onStausDropDownExpanded(expanded: Boolean) {
        statusDropDownExpand.value = expanded
    }

    fun onCategorySelected(category: TaskCategory) {
        selectedCategory.value = category
    }

    fun onCategoryChanged(taskId: Long, category: TaskCategory) {
        updateTaskPartial(taskId, taskCategory = category)
    }

    fun onPrioritySelected(priority: TaskPriority) {
        selectedPriority.value = priority
    }

    fun onPriorityChanged(taskId: Long, priority: TaskPriority) {
        updateTaskPartial(taskId, priority = priority)
    }

    fun onPriorityDropDownExpanded(expanded: Boolean) {
        priorityDropDownExpand.value = expanded
    }

    fun onCategoryDropDownExpanded(expanded: Boolean) {
        categoryDropDownExpand.value = expanded
    }

    fun onRepeatSelected(repeat: RepeatType) {
        selectedRepeatType.value = repeat
    }

    fun onRepeatDropDownExpanded(expanded: Boolean) {
        repeatDropDownExpand.value = expanded
    }


    fun getTaskDetail() {
        viewModelScope.launch {
            repository.getTask(navData.id).collect {
                _taskDetailFlow.value = it
            }
        }
    }

    fun updateTaskPartial(
        taskId: Long,
        title: String? = null,
        description: String? = null,
        isRepeatable: Boolean? = null,
        isSynced: Boolean? = null,
        markAsDelete: Boolean? = null,
        repeatType: RepeatType? = null,
        priority: TaskPriority? = null,
        taskStatus: TaskStatus? = null,
        taskCategory: TaskCategory? = null,
        dateTime: LocalDateTime? = null,
        updatedAt: LocalDateTime = currentLocalDateTime()
    ) {
        viewModelScope.launch {
            repository.updateTaskPartial(
                taskId,
                title,
                description,
                isRepeatable = isRepeatable,
                isSynced,
                markAsDelete,
                repeatType,
                priority,
                taskStatus,
                taskCategory,
                dateTime,
                updatedAt
            )

        }
    }

}