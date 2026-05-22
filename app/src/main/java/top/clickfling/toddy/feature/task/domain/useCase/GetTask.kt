package top.clickfling.toddy.feature.task.domain.useCase

import top.clickfling.toddy.feature.task.domain.model.Task
import top.clickfling.toddy.feature.task.domain.repository.TaskRepository

class GetTask(
  private val repository: TaskRepository
) {
  suspend operator fun invoke(id: Int): Task? {
    return repository.getTaskById(id)
  }
}