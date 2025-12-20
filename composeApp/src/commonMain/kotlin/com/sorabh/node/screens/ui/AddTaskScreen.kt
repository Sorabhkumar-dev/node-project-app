package com.sorabh.node.screens.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.sorabh.node.AppViewModel
import com.sorabh.node.components.AddInput
import com.sorabh.node.components.OutlinedAddInput
import com.sorabh.node.components.OutlinedDropdown
import com.sorabh.node.components.ShowDatePicker
import com.sorabh.node.components.ShowTimePicker
import com.sorabh.node.components.TaskAddedDialog
import com.sorabh.node.pojo.AppBar
import com.sorabh.node.screens.viewmodels.AddTaskViewModel
import com.sorabh.node.utils.AddTaskEvent
import com.sorabh.node.utils.ShowSnackBarEvent
import com.sorabh.node.utils.SnackBarEvent
import com.sorabh.node.utils.TaskCategory
import com.sorabh.node.utils.TaskPriority
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.add_new_task
import node.composeapp.generated.resources.add_task
import node.composeapp.generated.resources.description
import node.composeapp.generated.resources.repeating_task
import node.composeapp.generated.resources.title
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddTaskScreen(
    viewModel: AddTaskViewModel,
    sharedViewModel: AppViewModel,
    sendTopBarEvent: (AppBar) -> Unit,
    sendSnackBarEvent: (SnackBarEvent) -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        sharedViewModel.topBarEvent.collect {
            if (it is AddTaskEvent)
                if (viewModel.taskTitle.value.text.isBlank())
                    sendSnackBarEvent(
                        ShowSnackBarEvent(
                            "Add Something to Progress!",
                            Icons.Default.Close
                        )
                    )
                else {
                    keyboard?.hide()
                    viewModel.saveTask(sendSnackBarEvent = sendSnackBarEvent)
                }
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        sendTopBarEvent(
            AppBar(
                title = Res.string.add_new_task,
                icon = Icons.Default.Done,
                event = AddTaskEvent
            )
        )
    }

    AddTaskContent(viewModel = viewModel, sendSnackBarEvent = sendSnackBarEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTaskContent(
    viewModel: AddTaskViewModel,
    sendSnackBarEvent: (SnackBarEvent) -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            AddInput(
                modifier = Modifier.fillMaxWidth(),
                label = Res.string.title,
                maxLines = 2,
                minLines = 2,
                textFieldValue = viewModel.taskTitle.value,
                onValueChange = viewModel::onTaskTitleChanged
            )

            AddInput(
                modifier = Modifier.fillMaxWidth(),
                label = Res.string.description,
                textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                minLines = 4,
                maxLines = 10,
                textFieldValue = viewModel.taskDescription.value,
                onValueChange = viewModel::onTaskDescriptionChanged
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedAddInput(
                    modifier = Modifier.weight(0.46f),
                    imageVector = Icons.Default.CalendarMonth,
                    trailingIcon = Icons.Default.KeyboardArrowDown,
                    onIconBtnClick = {
                        viewModel.onDatePickerStateChanged(true)
                    },
                    text = viewModel.dateFormatter.format(viewModel.taskDate.value)
                )

                Spacer(modifier = Modifier.weight(0.08f))

                OutlinedAddInput(
                    modifier = Modifier.weight(0.46f),
                    imageVector = Icons.Default.Watch,
                    trailingIcon = Icons.Default.KeyboardArrowDown,
                    onIconBtnClick = {
                        viewModel.onTimePickerStateChanged(true)
                    },
                    text = viewModel.timeFormatter.format(viewModel.taskTime.value)
                )
            }

            ShowDatePicker(
                isShowDatePicker = viewModel.isShowDatePicker.value,
                onDismiss = viewModel::onDatePickerStateChanged
            ) {
                viewModel.onDatePickerStateChanged()
                viewModel.onTaskDateChanged(it)
            }

            ShowTimePicker(
                isShowTimePicker = viewModel.isShowTimePicker.value,
                onDismiss = viewModel::onTimePickerStateChanged
            ) {
                viewModel.onTimePickerStateChanged()
                viewModel.onTaskTimeChanged(it.hour, it.minute)
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedDropdown(
                    modifier = Modifier.weight(0.46f),
                    items = TaskCategory.entries,
                    itemLabel = { it.value },
                    selectedItem = viewModel.selectedTaskCategory.value,
                    onItemSelected = viewModel::onTaskCategorySelected
                )

                Spacer(modifier = Modifier.weight(0.08f))

                OutlinedDropdown(
                    modifier = Modifier.weight(0.46f),
                    items = TaskPriority.entries,
                    itemLabel = { it.name },
                    selectedItem = viewModel.selectTaskPriority.value,
                    onItemSelected = viewModel::onTaskPrioritySelected
                )

            }

            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Repeat, null)

                    Text(
                        text = stringResource(Res.string.repeating_task),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Switch(
                    checked = viewModel.isTaskRepeatable.value,
                    onCheckedChange = viewModel::onTaskRepeatableChanged
                )
            }
        }

        Button(
            onClick = {
                if (viewModel.taskTitle.value.text.isBlank())
                    sendSnackBarEvent(
                        ShowSnackBarEvent(
                            "Add Something to Progress!",
                            Icons.Default.Close
                        )
                    )
                else {
                    keyboard?.hide()
                    viewModel.saveTask(sendSnackBarEvent = sendSnackBarEvent)
                }
            },
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
            shape = MaterialTheme.shapes.small
        ) {
            Icon(imageVector = Icons.Default.Done, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(Res.string.add_task))
        }
    }

    if (viewModel.taskDialogVisibility.value)
        TaskAddedDialog(
            title = viewModel.taskTitle.value.text,
            description = viewModel.taskDescription.value.text,
            onDismiss = viewModel::onTaskDialogVisible
        )
}