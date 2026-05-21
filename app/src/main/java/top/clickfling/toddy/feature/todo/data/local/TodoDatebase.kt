package top.clickfling.toddy.feature.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [TodoEntity::class],
  version = 1
)
abstract class TodoDatebase : RoomDatabase() {
  abstract val todoDao: TodoDao

  companion object {
    const val DATABASE_NAME = "todos_db"
  }
}