package com.sorabh.node

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.sorabh.node.data.database.TaskDao
import com.sorabh.node.data.database.TaskEntity
import com.sorabh.node.data.database.type_converters.LocalDateTimeConverters
import com.sorabh.node.data.database.type_converters.PriorityConverter

@Database(entities = [TaskEntity::class], version = 2)
@TypeConverters(LocalDateTimeConverters::class, PriorityConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): TaskDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
