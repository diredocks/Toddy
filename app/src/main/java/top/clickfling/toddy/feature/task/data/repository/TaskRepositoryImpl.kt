package top.clickfling.toddy.feature.task.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import top.clickfling.toddy.feature.task.data.local.StepDao
import top.clickfling.toddy.feature.task.data.local.TaskDao
import top.clickfling.toddy.feature.task.data.local.toDomain
import top.clickfling.toddy.feature.task.data.local.toEntity
import top.clickfling.toddy.feature.task.domain.model.Step
import top.clickfling.toddy.feature.task.domain.model.Task
import top.clickfling.toddy.feature.task.domain.repository.TaskRepository

class TaskRepositoryImpl(
  private val taskDao: TaskDao,
  private val stepDao: StepDao,
) : TaskRepository {
  override fun getTasks(): Flow<List<Task>> {
    return taskDao.getTasks().map { entities -> entities.map { it.toDomain() } }
  }

  override suspend fun getTaskById(id: Int): Task? {
    return taskDao.getTaskById(id)?.toDomain()
  }

  override suspend fun insertTask(task: Task) {
    taskDao.insertTask(task.toEntity())
  }

  override suspend fun deleteTask(task: Task) {
    taskDao.deleteTask(task.toEntity())
  }

  override suspend fun insertStep(step: Step, taskId: Int) {
    stepDao.insertStep(step.toEntity(taskId))
  }

  override suspend fun updateStep(step: Step, taskId: Int) {
    stepDao.updateStep(step.toEntity(taskId))
  }

  override suspend fun deleteStep(stepId: String) {
    stepDao.deleteStepById(stepId)
  }

  override suspend fun deleteStepsByTaskId(taskId: Int) {
    stepDao.deleteStepsByTaskId(taskId)
  }

  override fun getStepsByTaskId(taskId: Int): Flow<List<Step>> {
    return stepDao.getStepsByTaskId(taskId).map { entities ->
      entities.map { it.toDomain() }
    }
  }
}