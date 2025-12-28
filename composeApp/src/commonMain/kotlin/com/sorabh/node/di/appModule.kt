package com.sorabh.node.di

import com.sorabh.node.data.remote.ApiRepository
import com.sorabh.node.data.remote.ApiRepositoryImpl
import com.sorabh.node.data.remote.ApiService
import com.sorabh.node.data.remote.ApiServiceImpl
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

    single<ApiService> { ApiServiceImpl(get()) }
    single<ApiRepository> { ApiRepositoryImpl(get(),get()) }

    viewModel { AppViewModel(get()) }
    viewModel { TodayTaskViewModel(get(),get()) }
    viewModel { AddTaskViewModel(get(), get()) }
    viewModel { ImportantTaskViewModel(get()) }
    viewModel { AllTaskViewModel(get()) }
    viewModel { RepeatTaskViewModel(get()) }
    viewModel { TaskDetailViewModel(get(), get()) }
}
