package top.clickfling.toddy.feature.todo.domain.repository

import kotlinx.coroutines.flow.Flow
import top.clickfling.toddy.feature.todo.domain.model.Todo

interface TodoRepository {
  fun getTodos(): Flow<List<Todo>>
  suspend fun getTodoById(id: Int): Todo?
  suspend fun insertTodo(todo: Todo)
  suspend fun deleteTodo(todo: Todo)
}