package top.clickfling.toddy.feature.task.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import top.clickfling.toddy.feature.task.domain.model.Task
import top.clickfling.toddy.feature.task.domain.repository.TaskRepository
import top.clickfling.toddy.feature.task.domain.util.TaskOrder

class GetTasks(
  private val repository: TaskRepository
) {
  operator fun invoke(
    taskOrder: TaskOrder,
    predicate: (Task) -> Boolean = { true }
  ): Flow<List<Task>> =
    // TODO: Sort in dao by providing corresponding methods
    repository.getTasks().map { tasks ->
      val ordered = when (taskOrder) {
        TaskOrder.Due -> tasks.sortedBy { it.due }
        TaskOrder.Alphabetically -> tasks.sortedBy { it.content }
        TaskOrder.Creation -> tasks.sortedBy { it.creation }
      }

      ordered.filter(predicate).sortedBy { it.completed }
    }
}