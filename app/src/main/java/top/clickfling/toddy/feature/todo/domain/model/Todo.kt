package top.clickfling.toddy.feature.todo.domain.model

data class Todo(
  val completed: Boolean = false,
  val content: String,
  val creation: Long,
  val due: Long? = null,
  val remind: Long? = null,
  val id: Int? = null,
) {}

class InvalidTodoException(message: String): Exception(message)