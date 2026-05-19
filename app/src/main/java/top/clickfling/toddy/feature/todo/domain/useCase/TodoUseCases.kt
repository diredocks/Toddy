package top.clickfling.toddy.feature.todo.domain.useCase

data class TodoUseCases(
  val getTodo: GetTodo,
  val getTodos: GetTodos,
  val addTodo: AddTodo,
  val deleteTodo: DeleteTodo,
)