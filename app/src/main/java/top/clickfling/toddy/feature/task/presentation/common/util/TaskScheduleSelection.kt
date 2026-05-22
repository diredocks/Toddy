package top.clickfling.toddy.feature.task.presentation.common.util

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.Instant

sealed interface TaskScheduleSelection {
  object Today : TaskScheduleSelection
  object Tomorrow : TaskScheduleSelection
  object NextWeek : TaskScheduleSelection
  data class Custom(val timestamp: Instant?) : TaskScheduleSelection
  object Clear : TaskScheduleSelection
}

fun TaskScheduleSelection.toDueDays(
  clock: Clock = Clock.System,
  timeZone: TimeZone = TimeZone.currentSystemDefault(),
): LocalDate? {
  val today = clock.todayIn(timeZone)
  return when (this) {
    TaskScheduleSelection.Clear -> null
    TaskScheduleSelection.Today -> today
    TaskScheduleSelection.Tomorrow -> today.plus(1, DateTimeUnit.DAY)
    TaskScheduleSelection.NextWeek -> today.plus(1, DateTimeUnit.WEEK)
    is TaskScheduleSelection.Custom -> {
      if (timestamp == null) return null
      timestamp.toLocalDateTime(timeZone).date
    }
  }
}

fun TaskScheduleSelection.toRemindTime(
  clock: Clock = Clock.System,
  timeZone: TimeZone = TimeZone.currentSystemDefault(),
  defaultReminderTime: LocalTime = LocalTime(9, 0)
): Instant? {
  val nowInstant = clock.now()
  val now = nowInstant.toLocalDateTime(timeZone)

  return when (this) {
    TaskScheduleSelection.Clear -> null
    TaskScheduleSelection.Today -> {
      val threeHoursLater =
        nowInstant.plus(3, DateTimeUnit.HOUR, timeZone).toLocalDateTime(timeZone)
      val sharpTime = LocalTime(threeHoursLater.hour, 0, 0, 0)
      threeHoursLater.date.atTime(sharpTime).toInstant(timeZone)
    }

    TaskScheduleSelection.Tomorrow ->
      now.date.plus(1, DateTimeUnit.DAY).atTime(defaultReminderTime).toInstant(timeZone)

    TaskScheduleSelection.NextWeek ->
      now.date.plus(1, DateTimeUnit.WEEK).atTime(defaultReminderTime).toInstant(timeZone)

    is TaskScheduleSelection.Custom -> this.timestamp
  }
}
