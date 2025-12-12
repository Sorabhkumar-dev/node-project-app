package com.sorabh.node.screens.ui

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.sorabh.node.pojo.AppBar
import com.sorabh.node.screens.viewmodels.TaskDetailViewModel
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
    val taskDetail = viewModel.taskDetailFlow.collectAsState(initial = null).value
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Faster Corp new project implementation and planing",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(2.dp))

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
            Spacer(modifier = Modifier.height(8.dp))

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
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Faster Corp new project implementation and planing.I have to plan and create proposal for creating new project regarding deep tech related advance AI/ML this project will be huge impact on Faster Corp tech capability and new stream.",
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                taskDetail?.taskStatus?.let {
                    ElevatedFilterChip(
                        onClick = {},
                        colors = FilterChipDefaults.elevatedFilterChipColors(
                            labelColor = MaterialTheme.colorScheme.primary.main,
                            iconColor = MaterialTheme.colorScheme.primary.main
                        ),
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
                        colors = FilterChipDefaults.elevatedFilterChipColors(
                            labelColor = MaterialTheme.colorScheme.primary.main,
                            iconColor = MaterialTheme.colorScheme.primary.main
                        ),
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
                        colors = FilterChipDefaults.elevatedFilterChipColors(
                            labelColor = MaterialTheme.colorScheme.primary.main,
                            iconColor = MaterialTheme.colorScheme.primary.main
                        ),
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