package top.clickfling.toddy.feature.task.data.local.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class LocalDateConverter {
  @TypeConverter
  fun toLong(value: LocalDate?): Long? =
    value?.toEpochDays()

  @TypeConverter
  fun fromLong(value: Long?): LocalDate? =
    value?.let(LocalDate::fromEpochDays)
}