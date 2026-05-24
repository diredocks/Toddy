package top.clickfling.toddy.feature.task.domain.useCase

import top.clickfling.toddy.feature.task.domain.model.Step
import top.clickfling.toddy.feature.task.domain.repository.TaskRepository

class UpdateStep(private val repository: TaskRepository) {
    suspend operator fun invoke(step: Step, taskId: Int) {
        repository.updateStep(step, taskId)
    }
}
