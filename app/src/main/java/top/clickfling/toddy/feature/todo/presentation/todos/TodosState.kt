package top.clickfling.toddy.feature.todo.presentation.todos

import top.clickfling.toddy.feature.todo.domain.model.Todo
import top.clickfling.toddy.feature.todo.domain.util.TodoOrder

data class TodosState (
  val todos: List<Todo> = emptyList(),
  val todoOrder: TodoOrder = TodoOrder.Due,
  val showCompleted: Boolean = false,
  val beforeTimestamp: Long? = null,
)