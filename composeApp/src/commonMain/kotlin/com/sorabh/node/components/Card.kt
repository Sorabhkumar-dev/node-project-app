package com.sorabh.node.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration.Companion.LineThrough
import androidx.compose.ui.text.style.TextDecoration.Companion.None
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sorabh.node.database.TaskEntity
import com.sorabh.node.nav.TaskDetailNav
import com.sorabh.node.utils.RepeatType
import com.sorabh.node.utils.TaskStatus
import com.sorabh.node.utils.TaskCategory
import com.sorabh.node.utils.color
import com.sorabh.node.utils.container
import com.sorabh.node.utils.currentLocalDateTime
import com.sorabh.node.utils.formatTaskDate
import com.sorabh.node.utils.main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.delete
import node.composeapp.generated.resources.done
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TaskCard(
    task: TaskEntity,
    onClick: (TaskDetailNav) -> Unit = {}
) {

    val stripWidthDp = 6.dp

    ElevatedCard(
        onClick = { onClick(TaskDetailNav(task.id)) },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = MaterialTheme.shapes.small
    ) {

        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .drawBehind {
                    val stripWidthPx = stripWidthDp.toPx()
                    drawRect(
                        color = task.taskCategory.color.main,
                        topLeft = Offset.Zero,
                        size = Size(width = stripWidthPx, height = size.height)
                    )
                }
                .background(task.taskCategory.color.container, MaterialTheme.shapes.small)
                .padding(16.dp)
        ) {

            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textDecoration = if (task.dateTime.date < currentLocalDateTime().date) LineThrough else None
            )

            Spacer(modifier = Modifier.height(4.dp))

            // --- Body: Description ---
            if (task.description.isNotEmpty()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))
            } else {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // --- Footer: Time, Type Chip, Repeat ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatusBadge(
                    text=task.taskStatus.name,
                    containerColor = task.taskStatus.color.container,
                    color = task.taskStatus.color
                )

                Spacer(modifier = Modifier.width(8.dp))

                StatusBadge(
                    text = task.priority.name,
                    containerColor = task.priority.color.container,
                    color = task.priority.color
                )

                Spacer(modifier = Modifier.width(8.dp))
                // Time Element
                DetailIconText(
                    icon = Icons.Outlined.Schedule,
                    text = task.dateTime.formatTaskDate()
                )

                Spacer(modifier = Modifier.weight(1f))

                // Repeat Icon (only if repeatable)
                if (task.isRepeatable) {
                    Icon(
                        imageVector = Icons.Filled.Repeat,
                        contentDescription = "Repeat",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                // Task Type Chip (Minimal)
                Surface(
                    color = task.taskCategory.color.container,
                    shape = CircleShape
                ) {
                    Text(
                        text = task.taskCategory.name.lowercase().capitalize(),
                        color = task.taskCategory.color,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

// --- Helper Components ---
@Composable
fun DetailIconText(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// --- Helper Composable for the Status Badge ---
@Composable
fun StatusBadge(
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall,
    containerColor: Color,
    color: Color
) {
    Surface(
        color = containerColor,
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = text,
            color = color,
            style = textStyle,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            fontSize = 10.sp // Make it very small/minimal
        )
    }
}


// Helper for capitalizing enum names
fun String.capitalize() =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

// --- Preview ---

@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            TaskCard(
                task = TaskEntity(
                    email = "sorabhkumar@gmail.com",
                    title = "Finish Project Documentation",
                    description = "Write the technical specs and api references for the client.",
                    dateTime = currentLocalDateTime(),
                    taskCategory = TaskCategory.WORK,
                    createdAt = currentLocalDateTime(),
                    updatedAt = currentLocalDateTime()
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TaskCard(
                task = TaskEntity(
                    email = "sorabhkumar@gmail.com",
                    title = "Buy Groceries",
                    dateTime = currentLocalDateTime(),
                    taskCategory = TaskCategory.PERSONAL,
                    isRepeatable = true,
                    repeatType = RepeatType.WEEKLY,
                    createdAt = currentLocalDateTime(),
                    updatedAt = currentLocalDateTime()
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableTaskCard(
    task: TaskEntity,
    onDelete: (Long) -> Unit,
    onComplete: (TaskEntity) -> Unit,
    content: @Composable (TaskEntity) -> Unit // Pass your existing TaskCard here
) {
    val animationDuration = 1500
    var isRemoved by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val dismissState = rememberSwipeToDismissBoxState()
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { SwipeBackground(dismissState) },
        content = {
            AnimatedVisibility(
                !isRemoved,
                exit = shrinkVertically(
                    animationSpec = tween(durationMillis = animationDuration),
                    shrinkTowards = Alignment.Top
                ) + fadeOut(
                    animationSpec = tween(durationMillis = animationDuration)
                )
            ) {
                content(task)
            }
        },
        onDismiss = {
            coroutineScope.launch {
                when (it) {
                    SwipeToDismissBoxValue.StartToEnd -> {
                        isRemoved = true
                        onDelete(task.id)
                    }

                    SwipeToDismissBoxValue.EndToStart -> onComplete(task.copy(taskStatus = TaskStatus.DONE))

                    SwipeToDismissBoxValue.Settled -> {}
                }
                delay(animationDuration.toLong())
                dismissState.dismiss(SwipeToDismissBoxValue.Settled)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBackground(dismissState: SwipeToDismissBoxState) {
    val direction = dismissState.dismissDirection

    val color by animateColorAsState(
        when (dismissState.targetValue) {
            SwipeToDismissBoxValue.Settled -> Color.Transparent
            SwipeToDismissBoxValue.EndToStart -> Color(0xFF66BB6A) // Green (Done)
            SwipeToDismissBoxValue.StartToEnd -> Color(0xFFEF5350) // Red (Delete)
        },
        label = "SwipeColor"
    )

    val icon = when (dismissState.targetValue) {
        SwipeToDismissBoxValue.StartToEnd -> Icons.Default.Delete
        SwipeToDismissBoxValue.EndToStart -> Icons.Outlined.Check
        else -> Icons.Default.Cancel// Fallback
    }

    val alignment = when (direction) {
        SwipeToDismissBoxValue.StartToEnd -> Arrangement.Start
        SwipeToDismissBoxValue.EndToStart -> Arrangement.End
        else -> Arrangement.Center
    }

    val scale by animateFloatAsState(
        if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 0.75f else 1f,
        label = "IconScale"
    )

    // 2. The Background UI
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.small)
            .background(color)
            .padding(16.dp),
        horizontalArrangement = alignment,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (direction == SwipeToDismissBoxValue.StartToEnd) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .scale(scale)
                    .size(36.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(Res.string.delete),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        } else {
            Text(
                text = stringResource(Res.string.done),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .scale(scale)
                    .size(36.dp)
            )
        }
    }
}