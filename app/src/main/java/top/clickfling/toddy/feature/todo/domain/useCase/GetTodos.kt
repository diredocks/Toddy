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
      val filteredTodos = todos.filter(predicate)

      when (todoOrder) {
        TodoOrder.Due -> filteredTodos.sortedBy { it.due }
        TodoOrder.Alphabetically -> filteredTodos.sortedBy { it.content }
        TodoOrder.Creation -> filteredTodos.sortedBy { it.creation }
      }
    }
}