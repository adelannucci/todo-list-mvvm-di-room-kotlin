package com.alv.todo.data.repository

import com.alv.todo.data.room.ToDoDao
import com.alv.todo.domain.ToDo
import javax.inject.Inject


class ToDoDataRepository @Inject constructor(
        private val dao: ToDoDao
) : ToDoRepository {

    override fun insert(item: ToDo) = dao.insert(item)

    override fun update(item: ToDo) = dao.update(item)

    override fun delete(item: ToDo) = dao.delete(item)

    override fun listAll() = dao.listAll()

}