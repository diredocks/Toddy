package top.clickfling.toddy.feature.task.domain.repository

import kotlinx.coroutines.flow.Flow
import top.clickfling.toddy.feature.task.domain.model.Task

interface TaskRepository {
  fun getTasks(): Flow<List<Task>>
  suspend fun getTaskById(id: Int): Task?
  suspend fun insertTask(task: Task)
  suspend fun deleteTask(task: Task)
}