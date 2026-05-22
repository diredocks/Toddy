package top.clickfling.toddy.feature.task.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import top.clickfling.toddy.feature.task.data.local.TaskDao
import top.clickfling.toddy.feature.task.data.local.toDomain
import top.clickfling.toddy.feature.task.data.local.toEntity
import top.clickfling.toddy.feature.task.domain.model.Task
import top.clickfling.toddy.feature.task.domain.repository.TaskRepository

class TaskRepositoryImpl(
  private val dao: TaskDao
) : TaskRepository {
  override fun getTasks(): Flow<List<Task>> {
    return dao.getTasks().map { entities -> entities.map { it.toDomain() } }
  }

  override suspend fun getTaskById(id: Int): Task? {
    return dao.getTaskById(id)?.toDomain()
  }

  override suspend fun insertTask(task: Task) {
    dao.insertTask(task.toEntity())
  }

  override suspend fun deleteTask(task: Task) {
    dao.deleteTask(task.toEntity())
  }
}