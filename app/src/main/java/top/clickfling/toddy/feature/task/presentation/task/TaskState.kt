package top.clickfling.toddy.feature.task.presentation.task

import top.clickfling.toddy.feature.task.domain.model.Task
import kotlin.time.Instant

data class TaskState(
  val task: Task = Task(
    completed = false,
    content = "",
    creation = Instant.fromEpochMilliseconds(0)
  ),
)
