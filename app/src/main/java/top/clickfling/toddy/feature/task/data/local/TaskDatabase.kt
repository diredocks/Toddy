package top.clickfling.toddy.feature.task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import top.clickfling.toddy.feature.task.data.local.converter.InstantConverter
import top.clickfling.toddy.feature.task.data.local.converter.LocalDateConverter

@Database(
  entities = [TaskEntity::class, StepEntity::class],
  version = 2,
)
@TypeConverters(
  LocalDateConverter::class,
  InstantConverter::class,
)
abstract class TaskDatabase : RoomDatabase() {
  abstract val taskDao: TaskDao
  abstract val stepDao: StepDao

  companion object {
    const val DATABASE_NAME = "tasks_db"
  }
}