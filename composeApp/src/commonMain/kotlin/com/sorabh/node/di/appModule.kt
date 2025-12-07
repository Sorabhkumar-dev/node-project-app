package com.sorabh.node.di

import com.sorabh.node.AppViewModel
import com.sorabh.node.screens.viewmodels.AddTaskViewModel
import com.sorabh.node.screens.viewmodels.TodayTaskViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AppViewModel() }
    viewModel { TodayTaskViewModel() }
    viewModel { AddTaskViewModel(get()) }
}
