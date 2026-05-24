package top.clickfling.toddy.feature.task.domain.useCase

data class TaskUseCases(
  val getTask: GetTask,
  val getTasks: GetTasks,
  val addTask: AddTask,
  val deleteTask: DeleteTask,
  val addStep: AddStep,
  val updateStep: UpdateStep,
  val deleteStep: DeleteStep,
  val getStepsForTask: GetStepsForTask,
)