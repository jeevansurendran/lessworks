package com.jeevan.domain.task

import com.jeevan.data.task.TaskRepository
import com.jeevan.di.IoDispatcher
import com.jeevan.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val taskRepository: TaskRepository
) :
    UseCase<Pair<String, Boolean>, Boolean>(dispatcher) {
    override suspend fun execute(parameters: Pair<String, Boolean>): Boolean {
        return taskRepository.updateTaskStatus(parameters.first, parameters.second)
    }

}