package com.sorabh.node.database

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface TaskRepository {
    suspend fun insertTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
    fun isTitleExists(title: String): Boolean
    fun getImportantTasks(): Flow<List<TaskEntity>>
    fun getTodayTasks(start: LocalDateTime, end: LocalDateTime): Flow<List<TaskEntity>>
    fun getTask(id: Int): Flow<TaskEntity>
    fun getAllTasks(): Flow<List<TaskEntity>>
}

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun insertTask(task: TaskEntity) = taskDao.insertTask(task)
    override suspend fun updateTask(task: TaskEntity) = taskDao.updateTask(task)
    override suspend fun deleteTask(task: TaskEntity) = taskDao.deleteTask(task)
    override fun isTitleExists(title: String): Boolean = taskDao.isTitleExists(title)
    override fun getImportantTasks(): Flow<List<TaskEntity>> = taskDao.getImportantTasks()
    override fun getTodayTasks(start: LocalDateTime, end: LocalDateTime): Flow<List<TaskEntity>> =
        taskDao.getTodayTasks(start, end)

    override fun getTask(id: Int): Flow<TaskEntity> = taskDao.getTask(id)
    override fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()
}