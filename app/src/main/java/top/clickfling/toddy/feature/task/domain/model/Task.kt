package top.clickfling.toddy.feature.task.domain.model

import kotlinx.datetime.LocalDate
import kotlin.time.Instant

data class Task(
  val completed: Boolean = false,
  val important: Boolean = false,
  val content: String,
  val creation: Instant,
  val due: LocalDate? = null,
  val remind: Instant? = null,
  val steps: List<Step> = emptyList(),
  val id: Int? = null,
)

class InvalidTaskException(message: String) : Exception(message)