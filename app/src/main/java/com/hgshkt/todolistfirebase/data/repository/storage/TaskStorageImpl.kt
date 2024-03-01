package com.hgshkt.todolistfirebase.data.repository.storage

import com.google.firebase.database.FirebaseDatabase
import com.hgshkt.todolistfirebase.data.auth.FirebaseAuthHelper
import com.hgshkt.todolistfirebase.data.repository.storage.model.TaskDB
import kotlinx.coroutines.tasks.await

class TaskStorageImpl(
    private val firebaseAuthHelper: FirebaseAuthHelper
): TaskStorage {

    private val dbPath = "https://todolistfirebase-fd7bc-default-rtdb.europe-west1.firebasedatabase.app/"
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance(dbPath)

    private val defaultUserId = "unknown_user_id"
    private val tasksDatabaseKey = "tasks"

    override fun create(task: TaskDB) {
        database.reference
            .child(firebaseAuthHelper.account?.id ?: defaultUserId)
            .child(tasksDatabaseKey)
            .child(task.id)
            .setValue(task.description)
    }

    override suspend fun getTasks(): List<TaskDB> {
        val tasks = mutableListOf<TaskDB>()

        database.reference
            .child(firebaseAuthHelper.account?.id ?: defaultUserId)
            .child(tasksDatabaseKey)

            .get()
            .await()
            .children

            .forEach {
                val description = it.getValue(String::class.java)
                tasks.add(
                    TaskDB(it.key!!, description!!)
                )
            }

        return tasks
    }

    override fun delete(task: TaskDB) {
        database.reference
            .child(firebaseAuthHelper.account?.id ?: defaultUserId)
            .child(tasksDatabaseKey)
            .child(task.id)
            .removeValue()
    }
}