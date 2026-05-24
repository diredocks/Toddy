package top.clickfling.toddy.feature.task.data.local

import top.clickfling.toddy.feature.task.domain.model.Step
import top.clickfling.toddy.feature.task.domain.model.Task

fun TaskEntity.toDomain(): Task {
  return Task(
    completed = completed,
    important = important,
    content = content,
    due = due,
    creation = creation,
    remind = remind,
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
    id = id
  )
}

fun StepEntity.toDomain(): Step {
  return Step(
    id = id,
    content = content,
    completed = completed,
    order = order,
  )
}

fun Step.toEntity(taskId: Int): StepEntity {
  return StepEntity(
    id = id,
    content = content,
    completed = completed,
    taskId = taskId,
    order = order,
  )
}