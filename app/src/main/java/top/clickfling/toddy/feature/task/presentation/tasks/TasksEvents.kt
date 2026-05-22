package top.clickfling.toddy.feature.task.presentation.tasks

import top.clickfling.toddy.feature.task.domain.model.Task
import top.clickfling.toddy.feature.task.domain.util.TaskOrder
import top.clickfling.toddy.feature.task.presentation.tasks.util.ChipSelection

sealed class TasksEvents {
  data class Order(val taskOrder: TaskOrder) : TasksEvents()
  data class DeleteTask(val task: Task) : TasksEvents()
  data class EnteredContent(val content: String) : TasksEvents()
  data class ToggleCompleted(val task: Task) : TasksEvents()
  data class ToggleImportance(val task: Task): TasksEvents()
  data class SelectDue(val dueSelection: ChipSelection) : TasksEvents()
  data class SelectRemind(val remindSelection: ChipSelection) : TasksEvents()
  object RestoreTask : TasksEvents()
  object ToggleCompletedVisibility : TasksEvents()
  object ToggleSheetVisibility : TasksEvents()
  object SaveTask : TasksEvents()
}