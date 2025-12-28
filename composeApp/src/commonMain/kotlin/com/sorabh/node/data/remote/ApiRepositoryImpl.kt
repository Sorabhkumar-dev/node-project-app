package com.sorabh.node.data.remote

import com.sorabh.node.data.database.TaskDao
import com.sorabh.node.data.remote.pojo.request.SyncRequest
import com.sorabh.node.data.remote.pojo.toTaskDTO
import com.sorabh.node.data.remote.pojo.toTaskEntity
import kotlinx.datetime.LocalDateTime

class ApiRepositoryImpl(
    private val taskDao: TaskDao,
    private val apiService: ApiService
) : ApiRepository {
    override suspend fun syncTasks(): Boolean{
       return try {
            val localUnsynced = taskDao.getUnsyncedTasks()
            val lastLocalUpdate = taskDao.getLastUpdatedTime() ?: LocalDateTime.parse("1970-01-01T00:00:00")

            val response = apiService.syncTask(
                SyncRequest(
                    lastSyncedAt = lastLocalUpdate,
                    clientTasks = localUnsynced.map { it.toTaskDTO() }
                )
            )

           print(response)


            // 1️⃣ Resolve conflicts (Last Update Wins)
            val resolvedTasks = response.serverTasks.map { serverTask ->
                val localTask = localUnsynced.find { it.id == serverTask.id }

                when {
                    localTask == null -> serverTask

                    serverTask.updatedAt > localTask.updatedAt -> serverTask

                    else -> localTask.toTaskDTO()
                }
            }

            // 2️⃣ Save resolved data locally
            taskDao.upsertTasks(resolvedTasks.map { it.toTaskEntity() })

            // 3️⃣ Mark synced
            taskDao.markAsSynced(response.syncedIds)
            true
        }catch (e: Exception){
            print(e)
            false
        }
    }
}