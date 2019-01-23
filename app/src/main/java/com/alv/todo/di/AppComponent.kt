package com.alv.todo.di

import com.alv.todo.App
import com.alv.todo.di.module.AppModule
import com.alv.todo.di.module.data.RepositoryModule
import com.alv.todo.di.module.data.RoomModule
import com.alv.todo.di.module.ui.ActivityModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    AppModule::class,
    RoomModule::class,
    RepositoryModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

}