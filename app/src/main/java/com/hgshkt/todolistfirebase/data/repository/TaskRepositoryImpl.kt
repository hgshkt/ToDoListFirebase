package com.hgshkt.todolistfirebase.data.repository

import com.hgshkt.todolistfirebase.data.repository.dataGenerator.IdGenerator
import com.hgshkt.todolistfirebase.data.repository.models.CreateTaskData
import com.hgshkt.todolistfirebase.data.repository.storage.TaskStorage
import com.hgshkt.todolistfirebase.data.repository.storage.model.TaskDB

class TaskRepositoryImpl(
    private val storage: TaskStorage,
    private val idGenerator: IdGenerator
) : TaskRepository {
    override fun create(data: CreateTaskData) {
        val task = TaskDB(
            id = idGenerator.randomUUID(),
            description = data.description
        )
        storage.create(task)
    }
}