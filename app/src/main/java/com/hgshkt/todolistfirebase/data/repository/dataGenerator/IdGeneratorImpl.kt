package com.hgshkt.todolistfirebase.data.repository.dataGenerator

import java.util.UUID

class IdGeneratorImpl: IdGenerator {
    override fun randomUUID(): String {
        return UUID.randomUUID().toString()
    }
}