package top.clickfling.toddy.feature.task.domain.model

data class Step(
  val id: String,
  val content: String = "",
  val completed: Boolean = false,
  val order: Int = 0,
)
