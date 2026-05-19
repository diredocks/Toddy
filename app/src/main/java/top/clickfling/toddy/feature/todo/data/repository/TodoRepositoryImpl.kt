package top.clickfling.toddy.feature.todo.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import top.clickfling.toddy.feature.todo.data.local.TodoDao
import top.clickfling.toddy.feature.todo.data.local.toDomain
import top.clickfling.toddy.feature.todo.data.local.toEntity
import top.clickfling.toddy.feature.todo.domain.model.Todo
import top.clickfling.toddy.feature.todo.domain.repository.TodoRepository

class TodoRepositoryImpl(
  private val dao: TodoDao
): TodoRepository {
  override fun getTodos(): Flow<List<Todo>> {
    return dao.getTodos().map { entities -> entities.map { it.toDomain() } }
  }

  override suspend fun getTodoById(id: Int): Todo? {
    return dao.getTodoById(id)?.toDomain()
  }

  override suspend fun insertTodo(todo: Todo) {
    dao.insertTodo(todo.toEntity())
  }

  override suspend fun deleteTodo(todo: Todo) {
    dao.deleteTodo(todo.toEntity())
  }
}