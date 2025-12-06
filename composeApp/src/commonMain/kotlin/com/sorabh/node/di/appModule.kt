package com.sorabh.node.di

import com.sorabh.node.AppViewModel
import com.sorabh.node.screens.viewmodels.AddTaskViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AppViewModel() }
    viewModel { AddTaskViewModel(get()) }
}
