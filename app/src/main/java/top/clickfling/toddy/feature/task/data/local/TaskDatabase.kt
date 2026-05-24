package top.clickfling.toddy.feature.task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import top.clickfling.toddy.feature.task.data.local.converter.InstantConverter
import top.clickfling.toddy.feature.task.data.local.converter.LocalDateConverter
import top.clickfling.toddy.feature.task.data.local.converter.StepListConverter

@Database(
  entities = [TaskEntity::class],
  version = 1
)
@TypeConverters(
  LocalDateConverter::class,
  InstantConverter::class,
  StepListConverter::class,
)
abstract class TaskDatabase : RoomDatabase() {
  abstract val taskDao: TaskDao

  companion object {
    const val DATABASE_NAME = "tasks_db"
  }
}