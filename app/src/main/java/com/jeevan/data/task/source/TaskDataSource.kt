package com.jeevan.data.task.source

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.jeevan.fragment.Task
import com.jeevan.mutation.CreateGroupTaskMutation
import com.jeevan.mutation.StatusTaskMutation
import com.jeevan.type.Task_insert_input
import java.util.*
import javax.inject.Inject

class TaskDataSource @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun createGroupTask(groupId: String, text: String, deadline: Date?  ): Task {
        val response = apolloClient.mutate(
            CreateGroupTaskMutation(
                groupId, Task_insert_input(
                    deadline = Input.fromNullable(deadline),
                    text = Input.fromNullable(text),
                )
            )
        ).await();
        return response.data?.group?.task?.fragments?.task!!
    }

    suspend fun updateTaskStatus(taskId: String, status: Boolean): Boolean {
        val response = apolloClient.mutate(
            StatusTaskMutation(
                taskId = taskId,
                status = Input.fromNullable(status)
            )
        ).await()
        return response.data?.task?.status!!
    }

    suspend fun createDirectTask(directId: String, text: String, deadline: Date?): Task {
        val response = apolloClient.mutate(
            com.jeevan.mutation.CreateDirectTaskMutation(
                directId,
                Task_insert_input(
                    deadline = Input.fromNullable(deadline),
                    text = Input.fromNullable(text),
                )
            )
        ).await();
        return response.data?.direct?.task?.fragments?.task!!
    }
}