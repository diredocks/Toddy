package top.clickfling.toddy.feature.task.presentation.task

import top.clickfling.toddy.feature.task.presentation.common.util.TaskScheduleSelection

sealed class TaskEvent {
  data class SelectDue(val dueSelection: TaskScheduleSelection) : TaskEvent()
  data class SelectRemind(val remindSelection: TaskScheduleSelection) : TaskEvent()
  object DeleteTask : TaskEvent()
  object ToggleCompleted : TaskEvent()
  object ToggleImportance : TaskEvent()
  data class OnContentChange(val content: String) : TaskEvent()
  data class AddStep(val content: String = "") : TaskEvent()
  data class UpdateStepContent(val stepId: String, val content: String) : TaskEvent()
  data class ToggleStepCompleted(val stepId: String) : TaskEvent()
}
