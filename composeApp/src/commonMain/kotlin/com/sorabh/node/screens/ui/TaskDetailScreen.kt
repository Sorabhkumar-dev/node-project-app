package com.sorabh.node.screens.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.sorabh.node.components.DropdownCard
import com.sorabh.node.pojo.AppBar
import com.sorabh.node.screens.viewmodels.TaskDetailViewModel
import com.sorabh.node.utils.RepeatType
import com.sorabh.node.utils.TaskCategory
import com.sorabh.node.utils.TaskPriority
import com.sorabh.node.utils.TaskStatus
import com.sorabh.node.utils.color
import com.sorabh.node.utils.formatTaskDate2
import com.sorabh.node.utils.icon
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.description
import node.composeapp.generated.resources.repeating_task
import node.composeapp.generated.resources.task_detail
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun TaskDetailScreen(viewModel: TaskDetailViewModel, sendTopBarEvent: (AppBar) -> Unit) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        sendTopBarEvent(AppBar(title = Res.string.task_detail))
        viewModel.getTaskDetail()
    }
    TaskDetailContent(viewModel = viewModel)
}

@Composable
private fun TaskDetailContent(viewModel: TaskDetailViewModel) {
    var isVisible by remember { mutableStateOf(false) }

    // 2. Trigger the animation when this Composable launches
    LaunchedEffect(Unit) {
        isVisible = true
    }
    val taskDetail = viewModel.taskDetailFlow.collectAsState(initial = null).value
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally(
                    // Start from -fullWidth (off-screen to the left)
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 1000)
                )
            ) {
                Text(
                    text = "Faster Corp new project implementation and planing",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }


            Spacer(modifier = Modifier.height(2.dp))

            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(durationMillis = 2000)
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = taskDetail?.dateTime?.formatTaskDate2() ?: "",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.ExtraLight
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally(
                    // Start from -fullWidth (off-screen to the left)
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 1000)
                )
            ) {
                Text(
                    text = stringResource(Res.string.description),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    // Start from -fullWidth (off-screen to the left)
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(durationMillis = 1000)
                )
            ) {
                Text(
                    text = "Faster Corp new project implementation and planing.I have to plan and create proposal for creating new project regarding deep tech related advance AI/ML this project will be huge impact on Faster Corp tech capability and new stream.",
                    textAlign = TextAlign.Justify
                )

            }
            Spacer(modifier = Modifier.height(8.dp))

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
                        viewModel.onRepeatTypeChanged(taskDetail?.id!!, it)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column {
                        if (taskDetail?.isRepeatable ?: false || viewModel.isTaskRepeatable.value) {
                            DropdownCard(
                                expanded = viewModel.repeatDropDownExpand.value,
                                onExpandedChange = viewModel::onRepeatDropDownExpanded,
                                selectedItem = viewModel.selectedRepeatType.value
                                    ?: taskDetail?.repeatType,
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
                                    viewModel.onRepeatTypeChanged(taskDetail?.id!!, true, it)
                                }
                            )


                        } else {
                            DropdownCard(
                                expanded = viewModel.priorityDropDownExpand.value,
                                onExpandedChange = viewModel::onPriorityDropDownExpanded,
                                selectedItem = viewModel.selectedPriority.value
                                    ?: taskDetail?.priority,
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
                                    viewModel.onPriorityChanged(taskId = taskDetail?.id!!, it)
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))


                        DropdownCard(
                            expanded = viewModel.statusDropDownExpand.value,
                            onExpandedChange = viewModel::onStausDropDownExpanded,
                            selectedItem = viewModel.selectedStatus.value
                                ?: taskDetail?.taskStatus,
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
                                viewModel.onStatusChanged(taskDetail?.id!!, it)
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
                                selectedItem = viewModel.selectedPriority.value
                                    ?: taskDetail?.priority,
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
                                    viewModel.onPriorityChanged(taskId = taskDetail?.id!!, it)
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        DropdownCard(
                            expanded = viewModel.categoryDropDownExpand.value,
                            onExpandedChange = viewModel::onCategoryDropDownExpanded,
                            selectedItem = viewModel.selectedCategory.value
                                ?: taskDetail?.taskCategory,
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
                                viewModel.onCategoryChanged(taskId = taskDetail?.id!!, it)
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}