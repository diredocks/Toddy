package top.clickfling.toddy.feature.todo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
  val completed: Boolean = false,
  val content: String,
  val creation: Long,
  val due: Long? = null,
  val remind: Long? = null,
  @PrimaryKey val id: Int? = null,
)