package top.clickfling.toddy.feature.task.domain.useCase

import top.clickfling.toddy.feature.task.domain.model.InvalidTaskException
import top.clickfling.toddy.feature.task.domain.model.Task
import top.clickfling.toddy.feature.task.domain.repository.TaskRepository

class AddTask(
  private val repository: TaskRepository
) {
  @Throws(InvalidTaskException::class)
  suspend operator fun invoke(task: Task) {
    if (task.content.isBlank()) {
      throw InvalidTaskException("The content of task can't be empty.")
    }
    repository.insertTask(task)
  }
}