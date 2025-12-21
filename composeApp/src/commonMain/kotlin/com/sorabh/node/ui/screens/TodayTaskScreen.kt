package com.sorabh.node.ui.screens

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
import com.sorabh.node.ui.viewmodels.AppViewModel
import com.sorabh.node.ui.components.EmptyTaskState
import com.sorabh.node.ui.components.ShowDateRangePicker
import com.sorabh.node.ui.components.SwipeableTaskCard
import com.sorabh.node.ui.components.TaskCard
import com.sorabh.node.ui.components.TaskFilterBottomSheet
import com.sorabh.node.ui.components.TaskFilterSheet
import com.sorabh.node.ui.nav.AddTaskNav
import com.sorabh.node.ui.nav.NavKey
import com.sorabh.node.ui.pojo.AppBar
import com.sorabh.node.ui.viewmodels.TodayTaskViewModel
import com.sorabh.node.ui.utils.FilterTaskEvent
import com.sorabh.node.ui.utils.TaskDateRange
import com.sorabh.node.ui.utils.standardFormat
import kotlinx.coroutines.launch
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.today_task
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayTaskScreen(
    sharedViewModel: AppViewModel,
    onAppBarChanged: (AppBar) -> Unit,
    onNavigate: (NavKey) -> Unit,
) {
    val viewModel = koinViewModel<TodayTaskViewModel>()
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
                title = Res.string.today_task,
                icon =  Icons.Outlined.FilterAlt,
                event = FilterTaskEvent(viewModel.isAnyFilterApplied)
            )
        )
    }
    TodayTaskContent(
        viewModel = viewModel,
        filterBottomSheet = filterBottomSheet,
        onNavigate = onNavigate
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodayTaskContent(
    viewModel: TodayTaskViewModel,
    filterBottomSheet: SheetState,
    onNavigate: (NavKey) -> Unit
) {
    val todayTasks = viewModel.todayTasks.collectAsState(emptyList()).value

    val coroutineScope = rememberCoroutineScope()
    val hideFilterSheet: () -> Unit = {
        coroutineScope.launch { filterBottomSheet.hide() }
    }

    if (todayTasks.isEmpty())
        EmptyTaskState {
            onNavigate(AddTaskNav())
        }
    else
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(todayTasks, key = { it.id }) {
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
                startDate = viewModel.startOfDay.value.standardFormat(),
                endDate = viewModel.startOfNextDay.value.standardFormat(),
                selectedStatus = viewModel.selectedStatus,
                selectedPriority = viewModel.selectedPriority,
                selectedCategory = viewModel.selectedCategory,
                selectedDataRange = viewModel.selectedDataRange.value,
                onStatusChanged = viewModel::onStatusChanged,
                onPriorityChanged = viewModel::onPriorityChanged,
                onCategoryChanged = viewModel::onCategoryChanged,
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
                viewModel.getTodayTasks()
                hideFilterSheet()
            }
        }
}