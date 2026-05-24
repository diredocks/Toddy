package top.clickfling.toddy.feature.task.presentation.tasks

import top.clickfling.toddy.feature.task.domain.model.Step
import top.clickfling.toddy.feature.task.domain.model.Task
import top.clickfling.toddy.feature.task.domain.util.TaskOrder
import top.clickfling.toddy.feature.task.presentation.common.util.TaskScheduleSelection

sealed class TasksEvents {
  data class Order(val taskOrder: TaskOrder) : TasksEvents()
  data class DeleteTask(val task: Task) : TasksEvents()
  data class EnteredContent(val content: String) : TasksEvents()
  data class ToggleCompleted(val task: Task) : TasksEvents()
  data class ToggleImportance(val task: Task) : TasksEvents()
  data class SelectDue(val dueSelection: TaskScheduleSelection) : TasksEvents()
  data class SelectRemind(val remindSelection: TaskScheduleSelection) : TasksEvents()
  data class StoreRecentlyDeletedTask(val task: Task, val steps: List<Step>) : TasksEvents()
  object RestoreTask : TasksEvents()
  object ToggleCompletedVisibility : TasksEvents()
  object ToggleSheetVisibility : TasksEvents()
  object SaveTask : TasksEvents()
  object DeleteRecentlyDeletedTask : TasksEvents()
}