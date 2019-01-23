package com.alv.todo.data.repository

import com.alv.todo.domain.ToDo
import io.reactivex.Maybe


interface ToDoRepository {

    fun insert(item: ToDo)

    fun update(item: ToDo)

    fun delete(item: ToDo)

    fun listAll(): Maybe<List<ToDo>>

}