package top.clickfling.toddy.feature.todo.presentation.todos

import top.clickfling.toddy.feature.todo.domain.model.Todo
import top.clickfling.toddy.feature.todo.domain.util.TodoOrder
import top.clickfling.toddy.feature.todo.presentation.todos.util.ChipSelection

sealed class TodosEvent {
  data class Order(val todoOrder: TodoOrder) : TodosEvent()
  data class DeleteTodo(val todo: Todo) : TodosEvent()
  data class EnteredContent(val content: String) : TodosEvent()
  data class ToggleTodoCompleted(val todo: Todo) : TodosEvent()
  data class SelectDue(val dueSelection: ChipSelection) : TodosEvent()
  data class SelectRemind(val remindSelection: ChipSelection) : TodosEvent()
  object RestoreTodo : TodosEvent()
  object ToggleCompletedVisibility : TodosEvent()
  object ToggleSheetVisibility : TodosEvent()
  object SaveTodo : TodosEvent()
}