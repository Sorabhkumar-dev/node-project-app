package com.sorabh.node.screens.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.sorabh.node.components.OutlinedAddInput
import com.sorabh.node.components.OutlinedDropdown
import com.sorabh.node.components.ShowDatePicker
import com.sorabh.node.components.ShowTimePicker
import com.sorabh.node.components.TaskAddedDialog
import com.sorabh.node.pojo.AppBar
import com.sorabh.node.screens.viewmodels.AddTaskViewModel
import com.sorabh.node.utils.AddTaskEvent
import com.sorabh.node.utils.RepeatType
import com.sorabh.node.utils.ShowSnackBarEvent
import com.sorabh.node.utils.SnackBarEvent
import com.sorabh.node.utils.TaskPriority
import com.sorabh.node.utils.TaskType
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.add_a_task
import node.composeapp.generated.resources.add_more_info
import node.composeapp.generated.resources.add_new_task
import node.composeapp.generated.resources.add_task
import node.composeapp.generated.resources.description
import node.composeapp.generated.resources.repeat
import node.composeapp.generated.resources.repeating_task
import node.composeapp.generated.resources.select_category
import node.composeapp.generated.resources.select_date
import node.composeapp.generated.resources.select_priority
import node.composeapp.generated.resources.select_time
import node.composeapp.generated.resources.task_description
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

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {

        Text(
            text = stringResource(resource = Res.string.title),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(2.dp))

        OutlinedAddInput(
            modifier = Modifier.fillMaxWidth(),
            placeHolder = Res.string.add_a_task,
            textFieldValue = viewModel.taskTitle.value,
            onValueChange = viewModel::onTaskTitleChanged
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(resource = Res.string.description),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(2.dp))

        OutlinedAddInput(
            modifier = Modifier.fillMaxWidth(),
            placeHolder = Res.string.task_description,
            minLines = 4,
            maxLines = 10,
            textFieldValue = viewModel.taskDescription.value,
            onValueChange = viewModel::onTaskDescriptionChanged
        )

        Spacer(modifier = Modifier.height(16.dp))



        AnimatedVisibility(!viewModel.isAddMoreInfo.value) {
            Button(
                onClick = {
                    viewModel.addMoreInfo(true)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = stringResource(Res.string.add_more_info))
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null
                )
            }

        }

        AnimatedVisibility(viewModel.isAddMoreInfo.value) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(resource = Res.string.select_date),
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedAddInput(
                    modifier = Modifier.fillMaxWidth(),
                    imageVector = Icons.Default.CalendarMonth,
                    onIconBtnClick = {
                        viewModel.onDatePickerStateChanged(true)
                    },
                    text = viewModel.dateFormatter.format(viewModel.taskDate.value)
                )

                ShowDatePicker(
                    isShowDatePicker = viewModel.isShowDatePicker.value,
                    onDismiss = viewModel::onDatePickerStateChanged
                ) {
                    viewModel.onDatePickerStateChanged()
                    viewModel.onTaskDateChanged(it)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(resource = Res.string.select_time),
                    fontWeight = FontWeight.SemiBold
                )


                OutlinedAddInput(
                    modifier = Modifier.fillMaxWidth(),
                    imageVector = Icons.Default.Watch,
                    onIconBtnClick = {
                        viewModel.onTimePickerStateChanged(true)
                    },
                    text = viewModel.timeFormatter.format(viewModel.taskTime.value)
                )

                ShowTimePicker(
                    isShowTimePicker = viewModel.isShowTimePicker.value,
                    onDismiss = viewModel::onTimePickerStateChanged
                ) {
                    viewModel.onTimePickerStateChanged()
                    viewModel.onTaskTimeChanged(it.hour, it.minute)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(resource = Res.string.select_category),
                    fontWeight = FontWeight.SemiBold
                )


                OutlinedDropdown(
                    items = TaskType.entries.toList(),
                    itemLabel = { it.value },
                    selectedItem = viewModel.selectedTaskCategory.value,
                    onItemSelected = viewModel::onTaskCategorySelected
                )


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(resource = Res.string.select_priority),
                    fontWeight = FontWeight.SemiBold
                )


                OutlinedDropdown(
                    items = TaskPriority.entries.toList(),
                    itemLabel = { it.name },
                    selectedItem = viewModel.selectTaskPriority.value,
                    onItemSelected = viewModel::onTaskPrioritySelected
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(Res.string.repeating_task),
                        fontWeight = FontWeight.SemiBold
                    )

                    Switch(
                        checked = viewModel.isTaskRepeatable.value,
                        onCheckedChange = viewModel::onTaskRepeatableChanged
                    )
                }


                AnimatedVisibility(viewModel.isTaskRepeatable.value) {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = stringResource(resource = Res.string.repeat),
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        OutlinedDropdown(
                            items = RepeatType.entries.toList(),
                            itemLabel = { it.value },
                            selectedItem = viewModel.selectRepeatType.value,
                            onItemSelected = viewModel::onRepeatTypeSelected
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

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
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                ) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(Res.string.add_task))
                }
            }
        }
    }

    if (viewModel.taskDialogVisibility.value)
        TaskAddedDialog(
            title = viewModel.taskTitle.value.text,
            description = viewModel.taskDescription.value.text,
            onDismiss = viewModel::onTaskDialogVisible
        )
}