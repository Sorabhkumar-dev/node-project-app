package com.sorabh.node.data.remote

interface ApiRepository {
    suspend fun syncTasks(): Boolean
}