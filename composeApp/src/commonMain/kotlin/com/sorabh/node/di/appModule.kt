package com.sorabh.node.di

import com.sorabh.node.ui.viewmodels.AppViewModel
import com.sorabh.node.ui.viewmodels.AddTaskViewModel
import com.sorabh.node.ui.viewmodels.AllTaskViewModel
import com.sorabh.node.ui.viewmodels.ImportantTaskViewModel
import com.sorabh.node.ui.viewmodels.RepeatTaskViewModel
import com.sorabh.node.ui.viewmodels.TaskDetailViewModel
import com.sorabh.node.ui.viewmodels.TodayTaskViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AppViewModel(get()) }
    viewModel { TodayTaskViewModel(get()) }
    viewModel { AddTaskViewModel(get(),get()) }
    viewModel { ImportantTaskViewModel(get()) }
    viewModel { AllTaskViewModel(get()) }
    viewModel { RepeatTaskViewModel(get()) }
    viewModel { TaskDetailViewModel(get(),get()) }
}
