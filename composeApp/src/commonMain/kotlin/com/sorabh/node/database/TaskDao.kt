package com.sorabh.node.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sorabh.node.utils.currentLocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("""
    UPDATE TaskEntity 
    SET markAsDelete = 1, updatedAt = :updatedAt
    WHERE id = :taskId
""")
    suspend fun deleteTask(taskId: Long,updatedAt: LocalDateTime = currentLocalDateTime())

    @Query("SELECT EXISTS(SELECT * FROM TaskEntity WHERE title = :title AND markAsDelete = 0)")
    fun isTitleExists(title: String): Boolean

    @Query("SELECT * FROM TaskEntity WHERE isRepeatable = 1 AND markAsDelete = 0")
    fun getRepeatingTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE markAsDelete = 0")
    fun getAllTask(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE isImportant = 1 AND markAsDelete = 0")
    fun getImportantTasks(): Flow<List<TaskEntity>>

    @Query(" SELECT * FROM TaskEntity WHERE dateTime BETWEEN :start AND :end AND markAsDelete = 0 ORDER BY dateTime DESC")
    fun getTodayTasks(start: LocalDateTime, end: LocalDateTime): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE id = :id AND markAsDelete = 0")
    fun getTask(id: Long): Flow<TaskEntity>

    @Query("SELECT * FROM TaskEntity  WHERE markAsDelete = 0 ORDER BY dateTime DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>
}