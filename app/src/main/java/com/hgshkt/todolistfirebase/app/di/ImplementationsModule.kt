package com.hgshkt.todolistfirebase.app.di

import com.hgshkt.todolistfirebase.data.repository.TaskRepository
import com.hgshkt.todolistfirebase.data.repository.TaskRepositoryImpl
import com.hgshkt.todolistfirebase.data.repository.dataGenerator.IdGenerator
import com.hgshkt.todolistfirebase.data.repository.dataGenerator.IdGeneratorImpl
import com.hgshkt.todolistfirebase.data.repository.storage.TaskStorage
import com.hgshkt.todolistfirebase.data.repository.storage.TaskStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ImplementationsModule {
    @Binds
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    abstract fun bindIdGenerator(
        idGeneratorImpl: IdGeneratorImpl
    ): IdGenerator

    @Binds
    abstract fun bindTaskStorage(
        taskStorageImpl: TaskStorageImpl
    ): TaskStorage
}