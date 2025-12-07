package com.sorabh.node.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query(" SELECT * FROM TaskEntity WHERE dateTime BETWEEN :start AND :end")
    fun getTodayTasks(start: LocalDateTime, end: LocalDateTime): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    fun getTask(id: Int): Flow<TaskEntity>

    @Query("SELECT * FROM TaskEntity ORDER BY dateTime DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>
}