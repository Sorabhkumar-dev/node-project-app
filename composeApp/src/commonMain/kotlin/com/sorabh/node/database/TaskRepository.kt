package com.sorabh.node.database

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun insertTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
    fun getTask(id: Int): Flow<TaskEntity>
    fun getAllTasks(): Flow<List<TaskEntity>>
}

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun insertTask(task: TaskEntity) = taskDao.insertTask(task)
    override suspend fun updateTask(task: TaskEntity) = taskDao.updateTask(task)
    override suspend fun deleteTask(task: TaskEntity) = taskDao.deleteTask(task)
    override fun getTask(id: Int): Flow<TaskEntity> = taskDao.getTask(id)
    override fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()
}