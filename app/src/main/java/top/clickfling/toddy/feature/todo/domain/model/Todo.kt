package top.clickfling.toddy.feature.todo.domain.model

import kotlinx.datetime.LocalDate
import kotlin.time.Instant

data class Todo(
  val completed: Boolean = false,
  val content: String,
  val creation: Instant,
  val due: LocalDate? = null,
  val remind: Instant? = null,
  val id: Int? = null,
)

class InvalidTodoException(message: String) : Exception(message)