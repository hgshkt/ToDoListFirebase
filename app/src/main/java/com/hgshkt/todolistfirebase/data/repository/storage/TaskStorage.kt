package com.hgshkt.todolistfirebase.data.repository.storage

import com.hgshkt.todolistfirebase.data.repository.storage.model.TaskDB

interface TaskStorage {
    fun create(task: TaskDB)

    suspend fun getTasks(): List<TaskDB>

    fun delete(task: TaskDB)
}