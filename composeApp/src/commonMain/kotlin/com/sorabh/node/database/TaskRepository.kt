package com.sorabh.node.database

import com.sorabh.node.utils.RepeatType
import com.sorabh.node.utils.TaskCategory
import com.sorabh.node.utils.TaskPriority
import com.sorabh.node.utils.TaskStatus
import com.sorabh.node.utils.currentLocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface TaskRepository {
    suspend fun insertTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(taskId: Long)
    suspend fun isTitleExists(title: String): Boolean
    fun getRepeatingTasks(): Flow<List<TaskEntity>>
    fun getAllTask(): Flow<List<TaskEntity>>
    fun getImportantTasks(): Flow<List<TaskEntity>>
    fun getTodayTasks(start: LocalDateTime, end: LocalDateTime): Flow<List<TaskEntity>>
    fun getTask(id: Long): Flow<TaskEntity>
    fun getAllTasks(): Flow<List<TaskEntity>>
    fun getFilteredTasks(
        filterStatus: Boolean,
        statuses: List<TaskStatus>,

        filterType: Boolean,
        types: List<TaskCategory>,

        filterPriority: Boolean,
        priorities: List<TaskPriority>,

        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?,
        isRepeatable: Boolean?
    ): Flow<List<TaskEntity>>

    suspend fun updateTaskPartial(
        taskId: Long,
        title: String?,
        description: String?,
        isRepeatable: Boolean?,
        isSynced: Boolean?,
        markAsDelete: Boolean?,
        repeatType: RepeatType?,
        priority: TaskPriority?,
        taskStatus: TaskStatus?,
        taskCategory: TaskCategory?,
        dateTime: LocalDateTime?,
        updatedAt: LocalDateTime = currentLocalDateTime()
    ): Int
}

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun insertTask(task: TaskEntity) = taskDao.insertTask(task)
    override suspend fun updateTask(task: TaskEntity) = taskDao.updateTask(task)
    override suspend fun deleteTask(taskId: Long) = taskDao.deleteTask(taskId)
    override suspend fun isTitleExists(title: String): Boolean = taskDao.isTitleExists(title)
    override fun getRepeatingTasks(): Flow<List<TaskEntity>> = taskDao.getRepeatingTasks()
    override fun getAllTask(): Flow<List<TaskEntity>> = taskDao.getAllTask()
    override fun getImportantTasks(): Flow<List<TaskEntity>> = taskDao.getImportantTasks()
    override fun getTodayTasks(start: LocalDateTime, end: LocalDateTime): Flow<List<TaskEntity>> =
        taskDao.getTodayTasks(start, end)

    override fun getTask(id: Long): Flow<TaskEntity> = taskDao.getTask(id)
    override fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()
    override fun getFilteredTasks(
        filterStatus: Boolean,
        statuses: List<TaskStatus>,

        filterType: Boolean,
        types: List<TaskCategory>,

        filterPriority: Boolean,
        priorities: List<TaskPriority>,

        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?,

        isRepeatable: Boolean?
    ): Flow<List<TaskEntity>> = taskDao.getFilteredTasks(
        filterStatus,
        statuses,
        filterType,
        types,
        filterPriority,
        priorities,
        startDateTime,
        endDateTime,
        isRepeatable
    )

    override suspend fun updateTaskPartial(
        taskId: Long,
        title: String?,
        description: String?,
        isRepeatable: Boolean?,
        isSynced: Boolean?,
        markAsDelete: Boolean?,
        repeatType: RepeatType?,
        priority: TaskPriority?,
        taskStatus: TaskStatus?,
        taskCategory: TaskCategory?,
        dateTime: LocalDateTime?,
        updatedAt: LocalDateTime
    ): Int =
        taskDao.updateTaskPartial(
            taskId,
            title,
            description,
            isRepeatable,
            isSynced,
            markAsDelete,
            repeatType,
            priority,
            taskStatus,
            taskCategory,
            dateTime,
            updatedAt
        )
}