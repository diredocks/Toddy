package top.clickfling.toddy.feature.task.domain.useCase

import top.clickfling.toddy.feature.task.domain.repository.TaskRepository

class DeleteStep(private val repository: TaskRepository) {
    suspend operator fun invoke(stepId: String) {
        repository.deleteStep(stepId)
    }
}
