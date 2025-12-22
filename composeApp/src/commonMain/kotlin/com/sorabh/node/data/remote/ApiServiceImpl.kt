package com.sorabh.node.data.remote

import com.sorabh.node.data.remote.pojo.request.SyncRequest
import com.sorabh.node.data.remote.pojo.response.SyncResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class ApiServiceImpl(private val ktorClient: HttpClient) : ApiService {
    override suspend fun syncTask(tasks: SyncRequest): SyncResponse =
            ktorClient.post("") {
                setBody(tasks)
            }.body()
}