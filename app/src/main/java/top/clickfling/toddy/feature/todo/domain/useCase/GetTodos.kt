package top.clickfling.toddy.feature.todo.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import top.clickfling.toddy.feature.todo.domain.model.Todo
import top.clickfling.toddy.feature.todo.domain.repository.TodoRepository
import top.clickfling.toddy.feature.todo.domain.util.TodoOrder

class GetTodos(
  private val repository: TodoRepository
) {
  operator fun invoke(
    todoOrder: TodoOrder,
    predicate: (Todo) -> Boolean = { true }
  ): Flow<List<Todo>> =
    // TODO: Sort in dao by providing corresponding methods
    repository.getTodos().map { todos ->
      val ordered = when (todoOrder) {
        TodoOrder.Due -> todos.sortedBy { it.due }
        TodoOrder.Alphabetically -> todos.sortedBy { it.content }
        TodoOrder.Creation -> todos.sortedBy { it.creation }
      }

      ordered.filter(predicate).sortedBy { it.completed }
    }
}