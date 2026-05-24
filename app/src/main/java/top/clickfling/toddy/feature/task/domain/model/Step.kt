package top.clickfling.toddy.feature.task.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Step(
  val id: String,
  val content: String = "",
  val completed: Boolean = false,
)
