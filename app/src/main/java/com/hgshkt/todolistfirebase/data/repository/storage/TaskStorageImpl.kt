package com.hgshkt.todolistfirebase.data.repository.storage

import com.google.firebase.database.FirebaseDatabase
import com.hgshkt.todolistfirebase.data.auth.FirebaseAuthHelper
import com.hgshkt.todolistfirebase.data.repository.storage.model.TaskDB

class TaskStorageImpl(
    private val firebaseAuthHelper: FirebaseAuthHelper,
    private val database: FirebaseDatabase
): TaskStorage {

    private val defaultUserId = "unknown_user_id"
    private val tasksDatabaseKey = "tasks"

    override fun create(task: TaskDB) {
        database.reference
            .child(firebaseAuthHelper.account?.id ?: defaultUserId)
            .child(tasksDatabaseKey)
            .child(task.id)
            .setValue(task.description)
    }
}