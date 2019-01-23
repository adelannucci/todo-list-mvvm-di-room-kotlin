package com.alv.todo.di.module.data

import com.alv.todo.data.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.arch.persistence.room.Room
import android.content.Context

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "app-db").build()

    @Provides
    @Singleton
    fun provideToDoDao(appDatabase: AppDatabase) = appDatabase.toDoDao()

}