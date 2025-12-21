package com.sorabh.node.data.remote

import org.koin.core.module.Module
import org.koin.dsl.module

actual val httpModule: Module
    get() = module {
        single { httpClient() }
    }