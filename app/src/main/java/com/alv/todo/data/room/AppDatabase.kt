package com.alv.todo.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.alv.todo.domain.ToDo


@Database(entities = [ToDo::class], version = AppDatabase.VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

    companion object {
        const val VERSION = 1
    }

}