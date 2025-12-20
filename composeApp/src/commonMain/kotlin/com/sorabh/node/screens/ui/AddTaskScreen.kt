package com.sorabh.node.screens.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.sorabh.node.AppViewModel
import com.sorabh.node.components.AddInput
import com.sorabh.node.components.DropdownCard
import com.sorabh.node.components.ElevatedCardItem
import com.sorabh.node.components.ShowDatePicker
import com.sorabh.node.components.ShowTimePicker
import com.sorabh.node.components.TaskAddedDialog
import com.sorabh.node.pojo.AppBar
import com.sorabh.node.screens.viewmodels.AddTaskViewModel
import com.sorabh.node.utils.AddTaskEvent
import com.sorabh.node.utils.RepeatType
import com.sorabh.node.utils.ShowSnackBarEvent
import com.sorabh.node.utils.SnackBarEvent
import com.sorabh.node.utils.TaskCategory
import com.sorabh.node.utils.TaskPriority
import com.sorabh.node.utils.TaskStatus
import com.sorabh.node.utils.color
import com.sorabh.node.utils.icon
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.add_new_task
import node.composeapp.generated.resources.add_task
import node.composeapp.generated.resources.description
import node.composeapp.generated.resources.repeating_task
import node.composeapp.generated.resources.title
import org.jetbrains.compose.resources.painterResource
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
    val taskDetail = viewModel.taskDetail.collectAsState(null).value
    val keyboard = LocalSoftwareKeyboardController.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item(span = { GridItemSpan(2) }) {
            AddInput(
                modifier = Modifier.fillMaxWidth(),
                label = Res.string.title,
                maxLines = 2,
                minLines = 2,
                textFieldValue = viewModel.taskTitle.value,
                onValueChange = viewModel::onTaskTitleChanged
            )
        }

        item(span = { GridItemSpan(2) }) {
            AddInput(
                modifier = Modifier.fillMaxWidth(),
                label = Res.string.description,
                textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                minLines = 4,
                maxLines = 10,
                textFieldValue = viewModel.taskDescription.value,
                onValueChange = viewModel::onTaskDescriptionChanged
            )
        }

        item {
            ElevatedCardItem(
                modifier = Modifier.fillMaxWidth(),
                label = viewModel.dateFormatter.format(viewModel.taskDate.value),
                leadingIcon = Icons.Default.CalendarMonth,
                trailing = Icons.Default.KeyboardArrowDown,
                onClick = {
                    viewModel.onDatePickerStateChanged(true)
                }
            )

            ShowDatePicker(
                isShowDatePicker = viewModel.isShowDatePicker.value,
                onDismiss = viewModel::onDatePickerStateChanged
            ) {
                viewModel.onDatePickerStateChanged()
                viewModel.onTaskDateChanged(it)
            }
        }


        item {
            ElevatedCardItem(
                modifier = Modifier.fillMaxWidth(),
                label = viewModel.timeFormatter.format(viewModel.taskTime.value),
                leadingIcon = Icons.Default.Watch,
                trailing = Icons.Default.KeyboardArrowDown,
                onClick = {
                    viewModel.onTimePickerStateChanged(true)
                }
            )

            ShowTimePicker(
                isShowTimePicker = viewModel.isShowTimePicker.value,
                onDismiss = viewModel::onTimePickerStateChanged
            ) {
                viewModel.onTimePickerStateChanged()
                viewModel.onTaskTimeChanged(it.hour, it.minute)
            }
        }



        item(span = { GridItemSpan(2) }) {
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
                    checked = (taskDetail?.isRepeatable ?: false || viewModel.isTaskRepeatable.value),
                    onCheckedChange = {
                        viewModel.onTaskRepeatableChanged(it)
                        viewModel.onRepeatTypeChanged(it)
                    }
                )
            }
        }

        item {
            Column {
                if (taskDetail?.isRepeatable ?: false || viewModel.isTaskRepeatable.value) {
                    DropdownCard(
                        expanded = viewModel.repeatDropDownExpand.value,
                        onExpandedChange = viewModel::onRepeatDropDownExpanded,
                        selectedItem = taskDetail?.repeatType ?: viewModel.selectedRepeatType.value,
                        items = RepeatType.entries,
                        label = {
                            Text(text = it.value)
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = null
                            )
                        },
                        onItemSelected = {
                            viewModel.onRepeatSelected(it)
                            viewModel.onRepeatTypeChanged( true, it)
                        }
                    )


                } else {
                    DropdownCard(
                        expanded = viewModel.priorityDropDownExpand.value,
                        onExpandedChange = viewModel::onPriorityDropDownExpanded,
                        selectedItem = taskDetail?.priority ?:  viewModel.selectedPriority.value,
                        items = TaskPriority.entries,
                        label = {
                            Text(text = it.name, color = it.color)
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = null,
                                tint = it.color
                            )
                        },
                        onItemSelected = {
                            viewModel.onPrioritySelected(it)
                            viewModel.onPriorityChanged( it)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))


                DropdownCard(
                    expanded = viewModel.statusDropDownExpand.value,
                    onExpandedChange = viewModel::onStausDropDownExpanded,
                    selectedItem = taskDetail?.taskStatus ?: viewModel.selectedStatus.value,
                    items = TaskStatus.entries,
                    label = {
                        Text(text = it.name, color = it.color)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = null,
                            tint = it.color
                        )
                    },
                    onItemSelected = {
                        viewModel.onStatusSelected(it)
                        viewModel.onStatusChanged( it)
                    }
                )
            }
        }

        item {
            Column {
                if ((taskDetail?.isRepeatable ?: false || viewModel.isTaskRepeatable.value)) {
                    DropdownCard(
                        expanded = viewModel.priorityDropDownExpand.value,
                        onExpandedChange = viewModel::onPriorityDropDownExpanded,
                        selectedItem = taskDetail?.priority ?: viewModel.selectedPriority.value,
                        items = TaskPriority.entries,
                        label = {
                            Text(text = it.name, color = it.color)
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = null,
                                tint = it.color
                            )
                        },
                        onItemSelected = {
                            viewModel.onPrioritySelected(it)
                            viewModel.onPriorityChanged( it)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                DropdownCard(
                    expanded = viewModel.categoryDropDownExpand.value,
                    onExpandedChange = viewModel::onCategoryDropDownExpanded,
                    selectedItem = taskDetail?.taskCategory ?: viewModel.selectedCategory.value,
                    items = TaskCategory.entries,
                    label = {
                        Text(text = it.name, color = it.color)
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(it.icon),
                            contentDescription = null,
                            tint = it.color
                        )
                    },
                    onItemSelected = {
                        viewModel.onCategorySelected(it)
                        viewModel.onCategoryChanged( it)
                    }
                )
            }
        }

        item (span = { GridItemSpan(2)}){
            Spacer(modifier = Modifier.height(24.dp))
        }

        item (span = { GridItemSpan(2)}){
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

    if (viewModel.taskDialogVisibility.value)
        TaskAddedDialog(
            title = viewModel.taskTitle.value.text,
            description = viewModel.taskDescription.value.text,
            onDismiss = viewModel::onTaskDialogVisible
        )

}