package top.clickfling.toddy.feature.todo.domain.useCase

import top.clickfling.toddy.feature.todo.domain.model.InvalidTodoException
import top.clickfling.toddy.feature.todo.domain.model.Todo
import top.clickfling.toddy.feature.todo.domain.repository.TodoRepository

class AddTodo(
  private val repository: TodoRepository
) {
  @Throws(InvalidTodoException::class)
  suspend operator fun invoke(todo: Todo) {
    if (todo.content.isBlank()) {
      throw InvalidTodoException("The content of todo can't be empty.")
    }
    repository.insertTodo(todo)
  }
}