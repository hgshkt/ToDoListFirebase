package com.hgshkt.todolistfirebase.data.repository

import com.hgshkt.todolistfirebase.data.repository.models.CreateTaskData
import com.hgshkt.todolistfirebase.data.repository.storage.model.TaskDB
import com.hgshkt.todolistfirebase.view.model.TaskDisplay

interface TaskRepository {
    fun create(data: CreateTaskData)

    suspend fun getTasks(): List<TaskDisplay>

    fun delete(task: TaskDisplay)
}