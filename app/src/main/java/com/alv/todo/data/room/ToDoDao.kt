package com.alv.todo.data.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.alv.todo.domain.ToDo
import io.reactivex.Maybe


@Dao
interface ToDoDao {

    @Insert
    fun insert(item: ToDo)

    @Update(onConflict = REPLACE)
    fun update(item: ToDo)

    @Delete
    fun delete(item: ToDo)

    @Query("SELECT * FROM todoList")
    fun listAll(): Maybe<List<ToDo>>

}