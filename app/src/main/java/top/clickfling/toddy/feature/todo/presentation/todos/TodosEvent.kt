package top.clickfling.toddy.feature.todo.presentation.todos

import top.clickfling.toddy.feature.todo.domain.model.Todo
import top.clickfling.toddy.feature.todo.domain.util.TodoOrder

sealed class TodosEvent {
  data class Order(val todoOrder: TodoOrder): TodosEvent()
  data class DeleteTodo(val todo: Todo): TodosEvent()
  object RestoreTodo: TodosEvent()
  object ToggleCompletedVisibility: TodosEvent()
  object  ToggleSheetVisibility: TodosEvent()
}