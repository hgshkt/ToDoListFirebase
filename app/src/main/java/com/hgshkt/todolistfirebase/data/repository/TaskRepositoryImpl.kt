package com.hgshkt.todolistfirebase.data.repository

import com.hgshkt.todolistfirebase.data.mapper.toDisplay
import com.hgshkt.todolistfirebase.data.repository.dataGenerator.IdGenerator
import com.hgshkt.todolistfirebase.data.repository.models.CreateTaskData
import com.hgshkt.todolistfirebase.data.repository.storage.TaskStorage
import com.hgshkt.todolistfirebase.data.repository.storage.model.TaskDB
import com.hgshkt.todolistfirebase.view.model.TaskDisplay

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

    override suspend fun getTasks(): List<TaskDisplay> {
        return storage.getTasks().map { it.toDisplay() }
    }
}