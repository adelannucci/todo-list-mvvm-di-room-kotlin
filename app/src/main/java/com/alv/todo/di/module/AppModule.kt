package com.alv.todo.di.module

import android.content.Context
import com.alv.todo.App
import com.alv.todo.di.module.ui.ViewModelModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: App): Context = app.applicationContext

}