package com.sorabh.node.ui.viewmodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.node.data.database.TaskEntity
import com.sorabh.node.data.database.TaskRepository
import com.sorabh.node.ui.nav.AddTaskNav
import com.sorabh.node.ui.utils.RepeatType
import com.sorabh.node.ui.utils.ShowSnackBarEvent
import com.sorabh.node.ui.utils.SnackBarEvent
import com.sorabh.node.ui.utils.TaskPriority
import com.sorabh.node.ui.utils.TaskCategory
import com.sorabh.node.ui.utils.TaskStatus
import com.sorabh.node.ui.utils.currentLocalDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class AddTaskViewModel(private val taskRepository: TaskRepository,private val navData: AddTaskNav) : ViewModel() {
    private val _taskDetail = MutableStateFlow<TaskEntity?>(null)
    val taskDetail = _taskDetail.asStateFlow()

    val taskTitle = mutableStateOf(TextFieldValue(""))
    val taskDescription = mutableStateOf(TextFieldValue(""))

    @OptIn(ExperimentalTime::class)
    private val currentDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

    @OptIn(ExperimentalTime::class)
    private val currentTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .time


    val dateFormatter = LocalDate.Format {
        day()
        char(' ')
        monthName(MonthNames.ENGLISH_ABBREVIATED) // e.g., Dec
        char(' ')
        year()
    }

    val timeFormatter = LocalTime.Format {
        hour()
        char(':')
        minute()
        char(' ')
        this.amPmMarker("AM", "PM")
    }

    val taskDate = mutableStateOf(currentDate)
    val taskTime = mutableStateOf(currentTime)

    val isShowDatePicker = mutableStateOf(false)
    val isShowTimePicker = mutableStateOf(false)

    val selectTaskPriority = mutableStateOf(navData.priority)

    val isTaskRepeatable = mutableStateOf(navData.isRepeatable)

    val selectRepeatType = mutableStateOf(RepeatType.DAILY)

    val taskDialogVisibility = mutableStateOf(false)

    val selectedRepeatType = mutableStateOf(RepeatType.DAILY)
    val repeatDropDownExpand = mutableStateOf(false)

    val selectedStatus = mutableStateOf(TaskStatus.TODO)
    val statusDropDownExpand = mutableStateOf(false)

    val selectedPriority = mutableStateOf(TaskPriority.MEDIUM)
    val priorityDropDownExpand = mutableStateOf(false)

    val selectedCategory = mutableStateOf(TaskCategory.OTHER)
    val categoryDropDownExpand = mutableStateOf(false)


    init {
        navData.taskId?.let { getTaskDetail(it) }
    }

    fun onTaskTitleChanged(title: TextFieldValue) {
        taskTitle.value = title
    }

    fun onTaskDescriptionChanged(description: TextFieldValue) {
        taskDescription.value = description
    }

    @OptIn(ExperimentalTime::class)
    fun onTaskDateChanged(date: Long?) {
        val instant: Instant = Instant.fromEpochMilliseconds(date!!)
        taskDate.value = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    fun onTaskTimeChanged(hour: Int, minute: Int) {

        taskTime.value =
            LocalTime.parse("${if (hour < 10) 0 else ""}$hour:${if (minute < 10) 0 else ""}$minute")
    }

    fun onDatePickerStateChanged(isShow: Boolean = false) {
        isShowDatePicker.value = isShow
    }

    fun onTimePickerStateChanged(isShow: Boolean = false) {
        isShowTimePicker.value = isShow
    }

    fun onTaskRepeatableChanged(isRepeatable: Boolean) {
        isTaskRepeatable.value = isRepeatable
    }

    fun onTaskDialogVisible(isVisible: Boolean = false) {
        taskDialogVisibility.value = isVisible
    }

    fun getTaskDetail(id: Long) {
        viewModelScope.launch {
            taskRepository.getTask(id).collect {
                _taskDetail.value = it
            }
        }
    }

    fun onRepeatTypeChanged(
        isRepeatable: Boolean,
        repeatType: RepeatType = RepeatType.DAILY
    ) {
        navData.taskId?.let {
            updateTaskPartial(it, isRepeatable = isRepeatable, repeatType = repeatType)
        }
    }

    fun onStatusSelected(status: TaskStatus) {
        selectedStatus.value = status
    }

    fun onStatusChanged(status: TaskStatus) {
        navData.taskId?.let {
            updateTaskPartial(it, taskStatus = status)
        }
    }

    fun onStausDropDownExpanded(expanded: Boolean) {
        statusDropDownExpand.value = expanded
    }

    fun onCategorySelected(category: TaskCategory) {
        selectedCategory.value = category
    }

    fun onCategoryChanged( category: TaskCategory) {
        navData.taskId?.let {
            updateTaskPartial(it, taskCategory = category)
        }
    }

    fun onPrioritySelected(priority: TaskPriority) {
        selectedPriority.value = priority
    }

    fun onPriorityChanged( priority: TaskPriority) {
        navData.taskId?.let {
            updateTaskPartial(it, priority = priority)
        }
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
            taskRepository.updateTaskPartial(
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

    fun saveTask(sendSnackBarEvent: (SnackBarEvent) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = TaskEntity(
                title = taskTitle.value.text,
                description = taskDescription.value.text,
                dateTime = LocalDateTime(taskDate.value, taskTime.value),
                priority = TaskPriority.entries[selectTaskPriority.value].name,
                taskCategory = selectedCategory.value.name,
                isRepeatable = isTaskRepeatable.value,
                repeatType = if (isTaskRepeatable.value) selectRepeatType.value.name else null,
                createdAt = currentLocalDateTime(),
                updatedAt = currentLocalDateTime()
            )
            if (taskRepository.isTitleExists(task.title))
                sendSnackBarEvent(ShowSnackBarEvent("Task Already Exists", Icons.Default.Close))
            else {
                taskRepository.insertTask(task)
                onTaskDialogVisible(true)
            }
        }
    }
}