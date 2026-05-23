package top.clickfling.toddy.feature.task.data.local

import top.clickfling.toddy.feature.task.domain.model.Task

fun TaskEntity.toDomain(): Task {
  return Task(
    completed = completed,
    important = important,
    content = content,
    due = due,
    creation = creation,
    remind = remind,
    steps = steps,
    id = id
  )
}

fun Task.toEntity(): TaskEntity {
  return TaskEntity(
    completed = completed,
    important = important,
    content = content,
    due = due,
    creation = creation,
    remind = remind,
    steps = steps,
    id = id
  )
}