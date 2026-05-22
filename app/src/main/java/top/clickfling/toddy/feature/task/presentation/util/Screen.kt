package top.clickfling.toddy.feature.task.presentation.util

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {
  @Serializable
  data object Tasks : Screen

  @Serializable
  data class Task(val taskId: Int? = null) : Screen
}
