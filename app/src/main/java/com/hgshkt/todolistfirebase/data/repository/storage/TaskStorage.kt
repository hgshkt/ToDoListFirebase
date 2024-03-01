package com.hgshkt.todolistfirebase.data.repository.storage

import com.hgshkt.todolistfirebase.data.repository.storage.model.TaskDB

interface TaskStorage {
    fun create(task: TaskDB)
}