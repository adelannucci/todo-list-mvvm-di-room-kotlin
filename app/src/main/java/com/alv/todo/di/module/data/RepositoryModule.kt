package com.alv.todo.di.module.data

import com.alv.todo.data.repository.ToDoDataRepository
import com.alv.todo.data.repository.ToDoRepository
import dagger.Binds
import dagger.Module


@Module
interface RepositoryModule {

    @Binds
    fun bindToDoRepository(imp: ToDoDataRepository): ToDoRepository

}