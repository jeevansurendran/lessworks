package com.jeevan.domain.task

import com.jeevan.data.task.TaskRepository
import com.jeevan.di.IoDispatcher
import com.jeevan.domain.UseCase
import com.jeevan.fragment.Task
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import javax.inject.Inject

class CreateDirectTaskUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val taskRepository: TaskRepository
) :
    UseCase<Triple<String, String, Date?>, Task>(dispatcher) {
    override suspend fun execute(parameters: Triple<String, String, Date?>): Task {
        return taskRepository.createDirectTask(parameters.first, parameters.second, parameters.third)
    }

}