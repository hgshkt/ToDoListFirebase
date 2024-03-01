package com.hgshkt.todolistfirebase.data.repository

import com.hgshkt.todolistfirebase.data.repository.models.CreateTaskData

interface TaskRepository {
    fun create(data: CreateTaskData)
}