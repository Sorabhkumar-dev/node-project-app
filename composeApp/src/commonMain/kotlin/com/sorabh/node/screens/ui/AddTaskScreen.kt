package com.sorabh.node.screens.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.sorabh.node.AppViewModel
import com.sorabh.node.components.AddInput
import com.sorabh.node.components.OutlinedDropdown
import com.sorabh.node.components.ShowDatePicker
import com.sorabh.node.components.ShowTimePicker
import com.sorabh.node.pojo.AppBar
import com.sorabh.node.screens.viewmodels.AddTaskViewModel
import com.sorabh.node.utils.RepeatType
import com.sorabh.node.utils.TaskType
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.add_a_task
import node.composeapp.generated.resources.add_more_info
import node.composeapp.generated.resources.add_task
import node.composeapp.generated.resources.choose_a_category
import node.composeapp.generated.resources.choose_a_day_to_take_action
import node.composeapp.generated.resources.choose_recurrence
import node.composeapp.generated.resources.do_you_want_to_return_this_task
import node.composeapp.generated.resources.give_your_task_its_perfect_moment
import node.composeapp.generated.resources.group_tasks_that_move_your_goals_forward
import node.composeapp.generated.resources.important_task
import node.composeapp.generated.resources.lets_add_something_to_get_done
import node.composeapp.generated.resources.recurring_task
import node.composeapp.generated.resources.should_this_task_repeat
import node.composeapp.generated.resources.tap_to_prioritize
import node.composeapp.generated.resources.what_do_you_want_to_accomplish
import node.composeapp.generated.resources.what_do_you_want_to_get_done
import node.composeapp.generated.resources.what_the_task
import node.composeapp.generated.resources.when_do_you_want_to_get_this_done
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddTaskScreen(viewModel: AddTaskViewModel, sharedViewModel: AppViewModel) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        sharedViewModel.onAppBarStateChanged(
            AppBar(
                title = Res.string.lets_add_something_to_get_done,
                icon = Icons.Default.Done
            )
        )
    }
    AddTaskContent(viewModel = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTaskContent(viewModel: AddTaskViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                text = stringResource(resource = Res.string.what_do_you_want_to_get_done),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(5.dp))

            AddInput(
                modifier = Modifier.fillMaxWidth(),
                label = Res.string.add_a_task,
                textFieldValue = viewModel.taskTitle.value,
                onValueChange = viewModel::onTaskTitleChanged
            )
        }

        item {
            Text(
                text = stringResource(resource = Res.string.what_do_you_want_to_accomplish),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(5.dp))

            AddInput(
                modifier = Modifier.fillMaxWidth(),
                label = Res.string.what_the_task,
                minLines = 3,
                maxLines = 3,
                textFieldValue = viewModel.taskDescription.value,
                onValueChange = viewModel::onTaskDescriptionChanged
            )
        }

        if (!viewModel.isAddMoreInfo.value)
            item {
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

        if (viewModel.isAddMoreInfo.value) {
            item {
                Text(
                    text = stringResource(resource = Res.string.when_do_you_want_to_get_this_done),
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(5.dp))

                AddInput(
                    modifier = Modifier.fillMaxWidth(),
                    imageVector = Icons.Default.CalendarMonth,
                    onIconBtnClick = {
                        viewModel.onDatePickerStateChanged(true)
                    },
                    label = Res.string.choose_a_day_to_take_action,
                    text = viewModel.dateFormatter.format(viewModel.taskDate.value)
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
                Text(
                    text = stringResource(resource = Res.string.when_do_you_want_to_get_this_done),
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(5.dp))

                AddInput(
                    modifier = Modifier.fillMaxWidth(),
                    imageVector = Icons.Default.Watch,
                    onIconBtnClick = {
                        viewModel.onTimePickerStateChanged(true)
                    },
                    label = Res.string.give_your_task_its_perfect_moment,
                    text = viewModel.timeFormatter.format(viewModel.taskTime.value)
                )

                ShowTimePicker(
                    isShowTimePicker = viewModel.isShowTimePicker.value,
                    onDismiss = viewModel::onTimePickerStateChanged
                ) {
                    viewModel.onTimePickerStateChanged()
                    viewModel.onTaskTimeChanged(it.hour, it.minute)
                }
            }

            item {
                Text(
                    text = stringResource(resource = Res.string.tap_to_prioritize),
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedDropdown(
                    label = stringResource(Res.string.important_task),
                    items = listOf(true, false),
                    selectedItem = viewModel.isTaskPriority.value,
                    onItemSelected = viewModel::onTaskPriorityChanged
                )
            }

            item {
                Text(
                    text = stringResource(resource = Res.string.group_tasks_that_move_your_goals_forward),
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedDropdown(
                    label = stringResource(Res.string.choose_a_category),
                    items = TaskType.entries.toList(),
                    itemLabel = { it.value },
                    selectedItem = viewModel.selectedTaskCategory.value,
                    onItemSelected = viewModel::onTaskCategorySelected
                )
            }

            item {
                Text(
                    text = stringResource(resource = Res.string.do_you_want_to_return_this_task),
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedDropdown(
                    label = stringResource(Res.string.should_this_task_repeat),
                    items = listOf(true, false),
                    selectedItem = viewModel.isTaskRepeatable.value,
                    onItemSelected = viewModel::onTaskRepeatableChanged
                )
            }

            if (viewModel.isTaskRepeatable.value)
                item {
                    Text(
                        text = stringResource(resource = Res.string.recurring_task),
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    OutlinedDropdown(
                        label = stringResource(Res.string.choose_recurrence),
                        items = RepeatType.entries.toList(),
                        itemLabel = { it.value },
                        selectedItem = viewModel.selectRepeatType.value,
                        onItemSelected = viewModel::onRepeatTypeSelected
                    )
                }

            item {
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(Res.string.add_task))
                }
            }
        }

    }
}