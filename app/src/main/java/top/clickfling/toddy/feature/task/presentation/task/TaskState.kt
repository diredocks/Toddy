package top.clickfling.toddy.feature.task.presentation.task

import kotlinx.datetime.LocalDate
import kotlin.time.Instant

data class TaskState(
  val content: String = "",
  val completed: Boolean = false,
  val important: Boolean = false,
  val due: LocalDate? = null,
  val remind: Instant? = null,
)
