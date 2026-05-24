package top.clickfling.toddy.feature.task.presentation.tasks

import kotlinx.datetime.LocalDate
import top.clickfling.toddy.feature.task.domain.model.Task
import top.clickfling.toddy.feature.task.domain.util.TaskOrder
import kotlin.time.Instant

data class TasksState(
  val tasks: List<Task> = emptyList(),
  val taskOrder: TaskOrder = TaskOrder.Due,
  val showCompleted: Boolean = false,
  val beforeTimestamp: Long? = null,
  val showSheet: Boolean = false,
  val recentlyDeletedTask: Task? = null,
  // new item related states
  val content: String = "",
  val due: LocalDate? = null,
  val remind: Instant? = null,
)
