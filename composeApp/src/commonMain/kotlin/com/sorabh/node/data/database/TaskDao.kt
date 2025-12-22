package com.sorabh.node.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sorabh.node.ui.utils.RepeatType
import com.sorabh.node.ui.utils.TaskCategory
import com.sorabh.node.ui.utils.TaskPriority
import com.sorabh.node.ui.utils.TaskStatus
import com.sorabh.node.ui.utils.currentLocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query(
        """
    UPDATE TaskEntity 
    SET markAsDelete = 1, updatedAt = :updatedAt
    WHERE id = :taskId
"""
    )
    suspend fun deleteTask(taskId: Long, updatedAt: LocalDateTime = currentLocalDateTime())

    @Query("SELECT EXISTS(SELECT * FROM TaskEntity WHERE title = :title AND markAsDelete = 0)")
    suspend fun isTitleExists(title: String): Boolean

    @Query("SELECT * FROM TaskEntity WHERE isRepeatable = 1 AND markAsDelete = 0")
    fun getRepeatingTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE markAsDelete = 0")
    fun getAllTask(): Flow<List<TaskEntity>>

    @Query(" SELECT * FROM TaskEntity WHERE priority = :priority AND markAsDelete = 0")
    fun getImportantTasks(priority: TaskPriority = TaskPriority.HIGH): Flow<List<TaskEntity>>

    @Query(" SELECT * FROM TaskEntity WHERE dateTime BETWEEN :start AND :end AND markAsDelete = 0 ORDER BY dateTime DESC")
    fun getTodayTasks(start: LocalDateTime, end: LocalDateTime): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE id = :id AND markAsDelete = 0")
    fun getTask(id: Long): Flow<TaskEntity>

    @Query("SELECT * FROM TaskEntity  WHERE markAsDelete = 0 ORDER BY dateTime DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query(
        """
    SELECT * FROM TaskEntity
    WHERE markAsDelete = 0
    AND (:isRepeatable IS NULL OR isRepeatable = :isRepeatable)
    AND (:filterStatus = 0 OR taskStatus IN (:statuses))
    AND (:filterType = 0 OR taskCategory IN (:types))
    AND (:filterPriority = 0 OR priority IN (:priorities))
    AND (:startDateTime IS NULL OR dateTime >= :startDateTime)
    AND (:endDateTime IS NULL OR dateTime <= :endDateTime)
    ORDER BY dateTime DESC
    """
    )
    fun getFilteredTasks(
        filterStatus: Boolean?,
        statuses: List<TaskStatus>,

        filterType: Boolean,
        types: List<TaskCategory>,

        filterPriority: Boolean,
        priorities: List<TaskPriority>,

        startDateTime: LocalDateTime?,
        endDateTime: LocalDateTime?,
        isRepeatable: Boolean?
    ): Flow<List<TaskEntity>>

    @Query(
        """
        UPDATE TaskEntity SET
            title = COALESCE(:title, title),
            description = COALESCE(:description, description),
            isRepeatable = COALESCE(:isRepeatable, isRepeatable),
            isSynced = COALESCE(:isSynced, isSynced),
            markAsDelete = COALESCE(:markAsDelete, markAsDelete),
            repeatType = COALESCE(:repeatType, repeatType),
            priority = COALESCE(:priority, priority),
            taskStatus = COALESCE(:taskStatus, taskStatus),
            taskCategory = COALESCE(:taskCategory, taskCategory),
            dateTime = COALESCE(:dateTime, dateTime),
            updatedAt = :updatedAt
        WHERE id = :taskId
        """
    )
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


    // Local → Server
    @Query("SELECT * FROM taskentity WHERE isSynced = 0")
    suspend fun getUnsyncedTasks(): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTasks(tasks: List<TaskEntity>)

    @Query("UPDATE taskentity SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<Long>)

    @Query("SELECT MAX(updatedAt) FROM taskentity")
    suspend fun getLastUpdatedTime(): LocalDateTime?

}