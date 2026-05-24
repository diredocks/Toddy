package top.clickfling.toddy.feature.task.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlin.time.Instant
import top.clickfling.toddy.feature.task.domain.model.Step

@Entity
data class TaskEntity(
  val completed: Boolean = false,
  val important: Boolean = false,
  val content: String,
  val creation: Instant,
  val due: LocalDate? = null,
  val remind: Instant? = null,
  val steps: List<Step> = emptyList(),
  @PrimaryKey val id: Int? = null,
)