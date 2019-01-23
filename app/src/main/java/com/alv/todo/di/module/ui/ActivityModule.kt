package com.alv.todo.di.module.ui

import com.alv.todo.ui.create.NewToDoActivity
import com.alv.todo.ui.list.ListTodoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface ActivityModule {

    @ContributesAndroidInjector
    fun contributeListToDoActivity(): ListTodoActivity

    @ContributesAndroidInjector
    fun contributeNewToDoActivity(): NewToDoActivity

}