package com.hgshkt.todolistfirebase.data.mapper

import com.hgshkt.todolistfirebase.data.repository.storage.model.TaskDB
import com.hgshkt.todolistfirebase.view.model.TaskDisplay

fun TaskDB.toDisplay(): TaskDisplay = TaskDisplay(
    id = id,
    description = description
)