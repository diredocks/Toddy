package top.clickfling.toddy.feature.todo.data.local

import top.clickfling.toddy.feature.todo.domain.model.Todo

fun TodoEntity.toDomain(): Todo {
  return Todo(
    completed = completed,
    content = content,
    due = due,
    creation = creation,
    remind = remind,
    id = id
  )
}

fun Todo.toEntity(): TodoEntity {
  return TodoEntity(
    completed = completed,
    content = content,
    due = due,
    creation = creation,
    remind = remind,
    id = id
  )
}