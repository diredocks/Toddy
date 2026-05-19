package top.clickfling.toddy.feature.todo.domain.model

data class Todo(
  val completed: Boolean = false,
  val content: String,
  val due: Long,
  val creation: Long,
  val remind: Long? = null,
  val id: Int? = null,
) {}

class InvalidTodoException(message: String): Exception(message)