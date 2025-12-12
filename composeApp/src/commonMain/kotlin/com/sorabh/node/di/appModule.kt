package com.sorabh.node.di

import com.sorabh.node.AppViewModel
import com.sorabh.node.screens.viewmodels.AddTaskViewModel
import com.sorabh.node.screens.viewmodels.AllTaskViewModel
import com.sorabh.node.screens.viewmodels.ImportantTaskViewModel
import com.sorabh.node.screens.viewmodels.RepeatTaskViewModel
import com.sorabh.node.screens.viewmodels.TaskDetailViewModel
import com.sorabh.node.screens.viewmodels.TodayTaskViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AppViewModel() }
    viewModel { TodayTaskViewModel(get()) }
    viewModel { AddTaskViewModel(get()) }
    viewModel { ImportantTaskViewModel(get()) }
    viewModel { AllTaskViewModel(get()) }
    viewModel { RepeatTaskViewModel(get()) }
    viewModel { TaskDetailViewModel(get(),get()) }
}
