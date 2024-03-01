package com.hgshkt.todolistfirebase.app.di

import com.hgshkt.todolistfirebase.data.auth.FirebaseAuthHelper
import com.hgshkt.todolistfirebase.data.repository.TaskRepositoryImpl
import com.hgshkt.todolistfirebase.data.repository.dataGenerator.IdGenerator
import com.hgshkt.todolistfirebase.data.repository.dataGenerator.IdGeneratorImpl
import com.hgshkt.todolistfirebase.data.repository.storage.TaskStorage
import com.hgshkt.todolistfirebase.data.repository.storage.TaskStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideTaskRepositoryImpl(
        storage: TaskStorage,
        idGenerator: IdGenerator
    ): TaskRepositoryImpl {
        return TaskRepositoryImpl(
            storage = storage,
            idGenerator = idGenerator
        )
    }

    @Provides
    @Singleton
    fun provideIdGeneratorImpl(): IdGeneratorImpl {
        return IdGeneratorImpl()
    }

    @Provides
    @Singleton
    fun provideTaskStorageImpl(
        firebaseAuthHelper: FirebaseAuthHelper
    ): TaskStorageImpl {
        return TaskStorageImpl(firebaseAuthHelper)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthHelper(): FirebaseAuthHelper{
        return FirebaseAuthHelper()
    }
}