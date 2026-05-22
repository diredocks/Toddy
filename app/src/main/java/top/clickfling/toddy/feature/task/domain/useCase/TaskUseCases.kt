package top.clickfling.toddy.feature.task.domain.useCase

data class TaskUseCases(
  val getTask: GetTask,
  val getTasks: GetTasks,
  val addTask: AddTask,
  val deleteTask: DeleteTask,
)