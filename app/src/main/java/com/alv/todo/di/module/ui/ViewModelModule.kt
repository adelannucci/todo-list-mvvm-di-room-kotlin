package com.alv.todo.di.module.ui

import android.arch.lifecycle.ViewModel
import com.alv.todo.ui.create.NewToDoViewModel
import com.alv.todo.ui.list.ListToDoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import viewmodel.dagger.ViewModelFactoryModule
import viewmodel.dagger.ViewModelKey


@Module(includes = [ViewModelFactoryModule::class])
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListToDoViewModel::class)
    fun bindListToDoViewModel(viewModel: ListToDoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewToDoViewModel::class)
    fun bindNewToDoViewModel(viewModel: NewToDoViewModel): ViewModel

}