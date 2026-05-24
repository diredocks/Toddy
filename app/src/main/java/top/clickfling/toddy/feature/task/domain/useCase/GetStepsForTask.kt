package top.clickfling.toddy.feature.task.domain.useCase

import kotlinx.coroutines.flow.Flow
import top.clickfling.toddy.feature.task.domain.model.Step
import top.clickfling.toddy.feature.task.domain.repository.TaskRepository

class GetStepsForTask(private val repository: TaskRepository) {
    operator fun invoke(taskId: Int): Flow<List<Step>> {
        return repository.getStepsByTaskId(taskId)
    }
}
