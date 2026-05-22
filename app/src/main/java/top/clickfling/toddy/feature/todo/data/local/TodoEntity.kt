package top.clickfling.toddy.feature.todo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlin.time.Instant

@Entity
data class TodoEntity(
  val completed: Boolean = false,
  val content: String,
  val creation: Instant,
  val due: LocalDate? = null,
  val remind: Instant? = null,
  @PrimaryKey val id: Int? = null,
)