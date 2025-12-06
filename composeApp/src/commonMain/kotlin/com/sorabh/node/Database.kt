package com.sorabh.node

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.sorabh.node.database.TaskDao
import com.sorabh.node.database.TaskEntity
import com.sorabh.node.database.type_converters.LocalDateTimeConverters

@Database(entities = [TaskEntity::class], version = 1)
@TypeConverters(LocalDateTimeConverters::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): TaskDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
