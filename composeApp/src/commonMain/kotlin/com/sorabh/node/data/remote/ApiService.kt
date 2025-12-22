package com.sorabh.node.data.remote

import com.sorabh.node.data.remote.pojo.request.SyncRequest
import com.sorabh.node.data.remote.pojo.response.SyncResponse

interface ApiService {
    suspend fun syncTask(tasks: SyncRequest): SyncResponse
}