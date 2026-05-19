package top.clickfling.toddy.feature.todo.domain.useCase

import top.clickfling.toddy.feature.todo.domain.model.Todo
import top.clickfling.toddy.feature.todo.domain.repository.TodoRepository

class GetTodo(
  private val repository: TodoRepository
) {
  suspend operator fun invoke(todo: Todo) {
    repository.deleteTodo(todo)
  }
}