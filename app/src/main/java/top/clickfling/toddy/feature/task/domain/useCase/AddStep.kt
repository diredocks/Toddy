package top.clickfling.toddy.feature.task.domain.useCase

import top.clickfling.toddy.feature.task.domain.model.Step
import top.clickfling.toddy.feature.task.domain.repository.TaskRepository

class AddStep(private val repository: TaskRepository) {
    suspend operator fun invoke(step: Step, taskId: Int) {
        if (step.content.isBlank()) return
        repository.insertStep(step, taskId)
    }
}
