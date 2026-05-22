package top.clickfling.toddy.feature.todo.presentation.todos.util

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

sealed interface ChipSelection {
  object Today : ChipSelection
  object Tomorrow : ChipSelection
  object NextWeek : ChipSelection
  data class Custom(val timestamp: Instant?) : ChipSelection
  object Clear : ChipSelection
}

fun ChipSelection.toDueDays(
  clock: Clock = Clock.System,
  timeZone: TimeZone = TimeZone.currentSystemDefault(),
): LocalDate? {
  val today = clock.todayIn(timeZone)
  return when (this) {
    ChipSelection.Clear -> null
    ChipSelection.Today -> today
    ChipSelection.Tomorrow -> today.plus(1, DateTimeUnit.DAY)
    ChipSelection.NextWeek -> today.plus(1, DateTimeUnit.WEEK)
    is ChipSelection.Custom -> {
      if (timestamp == null) return null
      timestamp.toLocalDateTime(timeZone).date
    }
  }
}

fun ChipSelection.toRemindTime(
  clock: Clock = Clock.System,
  timeZone: TimeZone = TimeZone.currentSystemDefault(),
  defaultReminderTime: LocalTime = LocalTime(9, 0)
): Instant? {
  val nowInstant = clock.now()
  val now = nowInstant.toLocalDateTime(timeZone)

  return when (this) {
    ChipSelection.Clear -> null
    ChipSelection.Today -> {
      val threeHoursLater =
        nowInstant.plus(3, DateTimeUnit.HOUR, timeZone).toLocalDateTime(timeZone)
      val sharpTime = LocalTime(threeHoursLater.hour, 0, 0, 0)
      threeHoursLater.date.atTime(sharpTime).toInstant(timeZone)
    }

    ChipSelection.Tomorrow -> now.date.plus(1, DateTimeUnit.DAY).atTime(defaultReminderTime)
      .toInstant(timeZone)

    ChipSelection.NextWeek -> now.date.plus(1, DateTimeUnit.WEEK).atTime(defaultReminderTime)
      .toInstant(timeZone)

    is ChipSelection.Custom -> this.timestamp
  }
}