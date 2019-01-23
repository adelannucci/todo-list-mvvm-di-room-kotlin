package com.alv.todo.domain

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "todoList")
data class ToDo(
        @PrimaryKey(autoGenerate = true) var id: Int?,
        var name: String,
        var isComplete: Boolean,
        var dateCreated: Long,
        var dateCompleted: Long
)