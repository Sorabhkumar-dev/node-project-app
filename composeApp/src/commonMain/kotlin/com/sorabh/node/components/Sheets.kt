package com.sorabh.node.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sorabh.node.utils.TaskDateRange
import com.sorabh.node.utils.TaskPriority
import com.sorabh.node.utils.TaskStatus
import com.sorabh.node.utils.TaskCategory
import com.sorabh.node.utils.color
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.category
import node.composeapp.generated.resources.filters
import node.composeapp.generated.resources.priority
import node.composeapp.generated.resources.select_date_range
import node.composeapp.generated.resources.show_task
import node.composeapp.generated.resources.status
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFilterBottomSheet(
    modifier: Modifier,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFilterSheet(
    modifier: Modifier,
    onDismiss: () -> Unit,
    startDate: String? = null,
    endDate: String? = null,
    selectedStatus: List<TaskStatus>,
    selectedPriority: List<TaskPriority>,
    selectedCategory: List<TaskCategory>,
    selectedDataRange: TaskDateRange?,
    onStatusChanged: (TaskStatus) -> Unit,
    onPriorityChanged: (TaskPriority) -> Unit,
    onCategoryChanged: (TaskCategory) -> Unit,
    onDateRangeClick: (TaskDateRange) -> Unit,
    onShowTaskClick: () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(Res.string.filters),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            IconButton(onClick = onDismiss) {
                Icon(imageVector = Icons.Filled.Close, null)
            }
        }

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.status),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TaskStatus.entries.forEach {
                ElevatedFilterChip(
                    selected = it in selectedStatus,
                    colors = FilterChipDefaults.elevatedFilterChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        selectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow

                    ),
                    onClick = { onStatusChanged(it) },
                    label = {
                        Text(
                            text = it.name,
                            color = if (it in selectedStatus) it.color else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.priority),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            fontWeight = FontWeight.SemiBold
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TaskPriority.entries.forEach {
                ElevatedFilterChip(
                    selected = it in selectedPriority,
                    onClick = { onPriorityChanged(it) },
                    colors = FilterChipDefaults.elevatedFilterChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        selectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow

                    ),
                    label = {
                        Text(
                            text = it.name,
                            color = if (it in selectedPriority) it.color else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.category),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            fontWeight = FontWeight.SemiBold
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TaskCategory.entries.forEach {
                ElevatedFilterChip(
                    selected = it in selectedCategory,
                    colors = FilterChipDefaults.elevatedFilterChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        selectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow

                    ),
                    onClick = {
                        onCategoryChanged(it)
                    },
                    label = {
                        Text(
                            text = it.name,
                            color = if (it in selectedCategory) it.color else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.select_date_range),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            fontWeight = FontWeight.SemiBold
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TaskDateRange.entries.forEach {
                ElevatedFilterChip(
                    selected = it == selectedDataRange,
                    colors = FilterChipDefaults.elevatedFilterChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        selectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow

                    ),
                    onClick = {
                        onDateRangeClick(it)
                    },
                    label = {
                        Text(
                            text = if (it == TaskDateRange.CUSTOM_RANGE && it == selectedDataRange) "$startDate - $endDate" else it.value,
                            color = if (it == selectedDataRange) Color(0xFF42A5F5) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }


            Spacer(modifier = Modifier.height(70.dp))

            Button(
                onClick = onShowTaskClick,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = stringResource(Res.string.show_task))
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}