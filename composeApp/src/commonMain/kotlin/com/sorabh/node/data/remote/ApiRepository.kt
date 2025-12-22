package com.sorabh.node.data.remote

import com.sorabh.node.data.remote.pojo.request.SyncRequest

interface ApiRepository {
    suspend fun syncTasks(request: SyncRequest): Boolean
}