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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.sorabh.node.pojo.AppBar
import com.sorabh.node.screens.viewmodels.TaskDetailViewModel
import com.sorabh.node.utils.color
import com.sorabh.node.utils.formatTaskDate2
import com.sorabh.node.utils.icon
import com.sorabh.node.utils.main
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.task_detail
import org.jetbrains.compose.resources.painterResource

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Description",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    if (taskDetail?.isImportant == true)
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            null,
                            modifier = Modifier.size(30.dp)
                        )
                }
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

            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 2000)
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    taskDetail?.taskStatus?.let {
                        ElevatedFilterChip(
                            onClick = {},
                            shape = MaterialTheme.shapes.medium,
                            colors = FilterChipDefaults.elevatedFilterChipColors(
                                labelColor = taskDetail.taskStatus.color,
                                iconColor = taskDetail.taskStatus.color
                            ),
                            elevation = FilterChipDefaults.elevatedFilterChipElevation(8.dp),
                            label = {
                                Text(text = it.name)
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = it.icon,
                                    contentDescription = null
                                )
                            },
                            selected = false
                        )
                    }

                    taskDetail?.taskType?.let {
                        ElevatedFilterChip(
                            onClick = {},
                            shape = MaterialTheme.shapes.medium,
                            colors = FilterChipDefaults.elevatedFilterChipColors(
                                labelColor = it.color,
                                iconColor = it.color
                            ),
                            elevation = FilterChipDefaults.elevatedFilterChipElevation(8.dp),
                            label = { Text(text = it.name) },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(it.icon),
                                    contentDescription = null
                                )
                            },
                            selected = false
                        )
                    }

                    if (taskDetail?.isRepeatable == true)
                        ElevatedFilterChip(
                            onClick = {},
                            shape = MaterialTheme.shapes.medium,
                            colors = FilterChipDefaults.elevatedFilterChipColors(
                                labelColor = MaterialTheme.colorScheme.primary.main,
                                iconColor = MaterialTheme.colorScheme.primary.main
                            ),
                            elevation = FilterChipDefaults.elevatedFilterChipElevation(8.dp),
                            label = {
                                Text(
                                    text = taskDetail.repeatType?.value ?: "",
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Repeat,
                                    contentDescription = null
                                )
                            },
                            selected = false
                        )
                }
            }
        }
    }
}