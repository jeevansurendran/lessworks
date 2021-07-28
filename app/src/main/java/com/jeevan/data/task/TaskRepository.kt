package com.jeevan.data.task

import com.jeevan.data.task.source.TaskDataSource
import com.jeevan.fragment.Task
import java.util.*
import javax.inject.Inject

class TaskRepository @Inject constructor(private val datasource: TaskDataSource) {
    suspend fun createGroupTask(groupId: String, text: String, deadline: Date?): Task {
        return datasource.createGroupTask(groupId, text, deadline)
    }

    suspend fun updateTaskStatus(taskId: String, status: Boolean): Boolean {
        return datasource.updateTaskStatus(taskId, status)
    }
}