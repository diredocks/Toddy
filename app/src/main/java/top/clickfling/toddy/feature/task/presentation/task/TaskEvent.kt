package top.clickfling.toddy.feature.task.presentation.task

import top.clickfling.toddy.feature.task.presentation.common.util.TaskScheduleSelection

sealed class TaskEvent {
  data class SelectDue(val dueSelection: TaskScheduleSelection) : TaskEvent()
  data class SelectRemind(val remindSelection: TaskScheduleSelection) : TaskEvent()
}
