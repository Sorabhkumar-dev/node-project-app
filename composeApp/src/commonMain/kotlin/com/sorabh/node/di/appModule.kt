package com.sorabh.node.di

import com.sorabh.node.AppViewModel
import com.sorabh.node.screens.viewmodels.AddTaskViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::AppViewModel)
    viewModelOf(::AddTaskViewModel)
}