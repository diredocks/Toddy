package top.clickfling.toddy.feature.todo.domain.useCase

import top.clickfling.toddy.feature.todo.domain.model.Todo
import top.clickfling.toddy.feature.todo.domain.repository.TodoRepository

class DeleteTodo(
  private val repository: TodoRepository
) {
  suspend operator fun invoke(todo: Todo) {
    repository.deleteTodo(todo)
  }
}