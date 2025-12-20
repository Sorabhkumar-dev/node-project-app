package com.sorabh.node.screens.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.sorabh.node.AppViewModel
import com.sorabh.node.components.EmptyTaskState
import com.sorabh.node.components.ShowDateRangePicker
import com.sorabh.node.components.SwipeableTaskCard
import com.sorabh.node.components.TaskCard
import com.sorabh.node.components.TaskFilterBottomSheet
import com.sorabh.node.components.TaskFilterSheet
import com.sorabh.node.nav.AddTaskNav
import com.sorabh.node.nav.NavKey
import com.sorabh.node.pojo.AppBar
import com.sorabh.node.screens.viewmodels.RepeatTaskViewModel
import com.sorabh.node.utils.FilterTaskEvent
import com.sorabh.node.utils.TaskDateRange
import com.sorabh.node.utils.standardFormat
import kotlinx.coroutines.launch
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.repeating_task
import node.composeapp.generated.resources.repeating_tasks
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepeatTaskScreen(
    sharedViewModel: AppViewModel,
    onAppBarChanged: (AppBar) -> Unit,
    onNavigate: (NavKey) -> Unit,
) {
    val viewModel = koinViewModel<RepeatTaskViewModel>()
    val filterBottomSheet = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        sharedViewModel.topBarEvent.collect {
            if (it is FilterTaskEvent)
                coroutineScope.launch {
                    filterBottomSheet.show()
                }

        }
    }

    LaunchedEffect(viewModel.isAnyFilterApplied) {
        onAppBarChanged(
            AppBar(
                title = Res.string.repeating_task,
                icon =  Icons.Outlined.FilterAlt,
                event = FilterTaskEvent(viewModel.isAnyFilterApplied)
            )
        )
    }

    LifecycleEventEffect(Lifecycle.Event.ON_CREATE){
        viewModel.getAllTasks()
    }
    RepeatTaskContent(viewModel = viewModel,filterBottomSheet = filterBottomSheet,onNavigate = onNavigate)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepeatTaskContent(viewModel: RepeatTaskViewModel, filterBottomSheet: SheetState, onNavigate: (NavKey) -> Unit) {
    val repeatingTasks = viewModel.repeatingTasks.collectAsState(emptyList()).value
    val coroutineScope = rememberCoroutineScope()
    val hideFilterSheet: () -> Unit = {
        coroutineScope.launch { filterBottomSheet.hide() }
    }

    if (repeatingTasks.isEmpty())
        EmptyTaskState(image = Res.drawable.repeating_tasks) {
            onNavigate(AddTaskNav(isRepeatable = true))
        }
    else
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(repeatingTasks, key = { it.id }) {
                SwipeableTaskCard(
                    task = it,
                    onDelete = viewModel::deleteTask,
                    onComplete = viewModel::updateTask
                ) { task ->
                    TaskCard(task = task, onClick = onNavigate)
                }
            }
        }



    if (viewModel.showDateRangePickerState.value)
        ShowDateRangePicker(
            onDateRangeSelected = {
                viewModel.onDateRangeSelected(it)
                viewModel.onDateRangePickerChanged(false)
            },
            onDismiss = viewModel::onDateRangePickerChanged
        )

    if (filterBottomSheet.isVisible)
        TaskFilterBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            sheetState = filterBottomSheet,
            onDismiss = { hideFilterSheet() }
        ) {
            TaskFilterSheet(
                modifier = Modifier.fillMaxWidth(),
                isRepeatable =  viewModel.isRepeatable.value,
                startDate = viewModel.startOfDay.value?.standardFormat(),
                endDate = viewModel.startOfNextDay.value?.standardFormat(),
                selectedStatus = viewModel.selectedStatus,
                selectedPriority = viewModel.selectedPriority,
                selectedCategory = viewModel.selectedCategory,
                selectedDataRange = viewModel.selectedDataRange.value,
                onStatusChanged = viewModel::onStatusChanged,
                onPriorityChanged = viewModel::onPriorityChanged,
                onCategoryChanged = viewModel::onCategoryChanged,
                onRepeatableClick = viewModel::onRepeatableClick,
                clearFilter = {
                    viewModel.resetFilters()
                    hideFilterSheet()
                },
                onDateRangeClick = {
                    viewModel.onTaskDateRangeChanged(it)
                    if (it == TaskDateRange.CUSTOM_RANGE)
                        viewModel.onDateRangePickerChanged(true)
                }
            ) {
                viewModel.getAllTasks()
                hideFilterSheet()
            }
        }
}