package com.sorabh.node.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sorabh.node.ui.screens.theme.AbsoluteBlack
import com.sorabh.node.ui.screens.theme.AbsoluteWhite
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.cancel
import node.composeapp.generated.resources.okay
import node.composeapp.generated.resources.select_time
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@Composable
fun ShowDateRangePicker(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                    onDismiss()
                }
            ) {
                Text(text = stringResource(Res.string.okay))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(Res.string.cancel))
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            showModeToggle = true,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun ShowDatePicker(
    isShowDatePicker: Boolean,
    isRefreshCalender: Boolean = false,
    onDismiss: () -> Unit,
    date: Long? = null,
    isGiveSelectableDate: Boolean = false,
    onDateSelected: (Long?) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = date ?: Clock.System.now().toEpochMilliseconds(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return if (isGiveSelectableDate) utcTimeMillis >= (date
                    ?: Clock.System.now().toEpochMilliseconds()) else true
            }
        }
    )

    LaunchedEffect(isRefreshCalender) {
        if (isRefreshCalender)
            datePickerState.selectedDateMillis = date

    }

    if (isShowDatePicker)
        DatePickerDialog(
            modifier = Modifier.background(
                MaterialTheme.colorScheme.onPrimary,
                MaterialTheme.shapes.small
            ),
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }
                ) {
                    Text(text = stringResource(Res.string.okay))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = stringResource(Res.string.cancel))
                }
            },
            shape = MaterialTheme.shapes.small,
            colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTimePicker(
    isShowTimePicker: Boolean, onDismiss: () -> Unit, onConfirm: (TimePickerState) -> Unit
) {
    val timePickerState = rememberTimePickerState()
    if (isShowTimePicker)
        ShowTimeDialog(
            onDismiss = onDismiss,
            onConfirm = { onConfirm(timePickerState) }
        ) {
            TimePicker(
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.shapes.small
                ),
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    // Background of the analog clock face
                    clockDialColor = AbsoluteWhite,

                    // The circle and arm that selects the time
                    selectorColor = AbsoluteBlack,

                    // The numbers on the clock face
                    clockDialSelectedContentColor = AbsoluteWhite,
                    clockDialUnselectedContentColor = AbsoluteBlack,

                    // The AM/PM switcher
                    periodSelectorBorderColor = AbsoluteBlack,
                    periodSelectorSelectedContainerColor = AbsoluteBlack,
                    periodSelectorUnselectedContainerColor = AbsoluteWhite,
                    periodSelectorSelectedContentColor = AbsoluteWhite,
                    periodSelectorUnselectedContentColor = AbsoluteBlack,

                    // The Hour/Minute input boxes at the top
                    timeSelectorSelectedContainerColor = AbsoluteBlack,
                    timeSelectorUnselectedContainerColor = AbsoluteWhite,
                    timeSelectorSelectedContentColor = AbsoluteWhite,
                    timeSelectorUnselectedContentColor = AbsoluteBlack
                )
            )
        }
}

@Composable
private fun ShowTimeDialog(
    onDismiss: () -> Unit, onConfirm: () -> Unit, content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outline,
                    MaterialTheme.shapes.small
                )
                .background(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.background
                ),
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    text = stringResource(Res.string.select_time),
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(Res.string.cancel))
                    }

                    TextButton(onClick = onConfirm) {
                        Text(text = stringResource(Res.string.okay))
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddedDialog(
    title: String,
    description: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {

        val infiniteTransition = rememberInfiniteTransition(label = "done_bounce")

        val scale by infiniteTransition.animateFloat(
            initialValue = 0.9f,
            targetValue = 1.15f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 600,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scale_anim"
        )

        Surface(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .widthIn(min = 260.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Done Icon
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(72.dp)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(20.dp))

                // OK Button
                OutlinedButton(onClick = onDismiss) {
                    Text("Done")
                }
            }
        }
    }
}
