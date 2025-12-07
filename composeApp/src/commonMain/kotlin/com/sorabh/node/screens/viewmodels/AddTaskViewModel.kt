package com.sorabh.node.screens.viewmodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.node.database.TaskEntity
import com.sorabh.node.database.TaskRepository
import com.sorabh.node.utils.RepeatType
import com.sorabh.node.utils.ShowSnackBarEvent
import com.sorabh.node.utils.SnackBarEvent
import com.sorabh.node.utils.TaskType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
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

class AddTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    val taskTitle = mutableStateOf(TextFieldValue(""))
    val taskDescription = mutableStateOf(TextFieldValue(""))

    val isAddMoreInfo = mutableStateOf(false)

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

    val isTaskPriority = mutableStateOf(false)

    val isShowDatePicker = mutableStateOf(false)
    val isShowTimePicker = mutableStateOf(false)

    val selectedTaskCategory = mutableStateOf(TaskType.WORK)

    val isTaskRepeatable = mutableStateOf(false)

    val selectRepeatType = mutableStateOf(RepeatType.DAILY)

    fun onTaskTitleChanged(title: TextFieldValue) {
        taskTitle.value = title
    }

    fun addMoreInfo(addMoreInfo: Boolean) {
        isAddMoreInfo.value = addMoreInfo
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

    fun onTaskPriorityChanged(isPriority: Boolean) {
        isTaskPriority.value = isPriority
    }

    fun onTaskCategorySelected(taskType: TaskType) {
        selectedTaskCategory.value = taskType
    }

    fun onTaskRepeatableChanged(isRepeatable: Boolean) {
        isTaskRepeatable.value = isRepeatable
    }

    fun onRepeatTypeSelected(repeatType: RepeatType) {
        selectRepeatType.value = repeatType
    }

    fun saveTask(sendSnackBarEvent: (SnackBarEvent) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = TaskEntity(
                title = taskTitle.value.text,
                description = taskDescription.value.text,
                dateTime = LocalDateTime(taskDate.value, taskTime.value),
                isImportant = isTaskPriority.value,
                taskType = selectedTaskCategory.value,
                isRepeatable = isTaskRepeatable.value,
                repeatType = if (isTaskRepeatable.value) selectRepeatType.value else null
            )
            if (taskRepository.isTitleExists(task.title))
                sendSnackBarEvent(ShowSnackBarEvent("Task Already Exists", Icons.Default.Close))
            else {
                taskRepository.insertTask(task)
                sendSnackBarEvent(ShowSnackBarEvent("Task \"${task.title}\" Added",Icons.Default.Done))
            }
        }
    }
}