package top.clickfling.toddy.feature.task.data.local.converter

import androidx.room.TypeConverter
import kotlin.time.Instant

class InstantConverter {
  @TypeConverter
  fun toLong(value: Instant?): Long? =
    value?.toEpochMilliseconds()

  @TypeConverter
  fun fromLong(value: Long?): Instant? =
    value?.let(Instant::fromEpochMilliseconds)
}