package top.clickfling.toddy.feature.task.domain.useCase

import top.clickfling.toddy.feature.task.domain.repository.TaskRepository

class DeleteStepsByTaskId(private val repository: TaskRepository) {
    suspend operator fun invoke(taskId: Int) {
        repository.deleteStepsByTaskId(taskId)
    }
}
