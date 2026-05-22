package top.clickfling.toddy.feature.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import top.clickfling.toddy.feature.todo.data.local.converter.InstantConverter
import top.clickfling.toddy.feature.todo.data.local.converter.LocalDateConverter

@Database(
  entities = [TodoEntity::class],
  version = 1
)
@TypeConverters(
  LocalDateConverter::class,
  InstantConverter::class
)
abstract class TodoDatebase : RoomDatabase() {
  abstract val todoDao: TodoDao

  companion object {
    const val DATABASE_NAME = "todos_db"
  }
}