package top.clickfling.toddy.feature.todo.presentation.todos.util

import kotlinx.datetime.DateTimeUnit
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
  data class Custom(val timestamp: Long?) : ChipSelection
  object Clear : ChipSelection
}

fun ChipSelection.toDueDays(
  clock: Clock = Clock.System,
  timeZone: TimeZone = TimeZone.currentSystemDefault(),
): Long? {
  val today = clock.todayIn(timeZone)
  return when (this) {
    ChipSelection.Clear -> null
    ChipSelection.Today -> today.toEpochDays()
    ChipSelection.Tomorrow -> today.plus(1, DateTimeUnit.DAY).toEpochDays()
    ChipSelection.NextWeek -> today.plus(1, DateTimeUnit.WEEK).toEpochDays()
    is ChipSelection.Custom -> {
      if (timestamp == null) return null
      Instant.fromEpochMilliseconds(timestamp).toLocalDateTime(timeZone).date.toEpochDays()
    }
  }
}

fun ChipSelection.toRemindTime(
  clock: Clock = Clock.System,
  timeZone: TimeZone = TimeZone.currentSystemDefault(),
  defaultReminderTime: LocalTime = LocalTime(9, 0)
): Long? {
  val nowInstant = clock.now()

  return when (this) {
    ChipSelection.Clear -> null

    ChipSelection.Today -> {
      val nowMs = nowInstant.toEpochMilliseconds()
      val oneHourMs = 60 * 60 * 1000L

      (nowMs / oneHourMs) * oneHourMs + (3 * oneHourMs)
    }

    ChipSelection.Tomorrow -> {
      val today = nowInstant.toLocalDateTime(timeZone).date
      today.plus(1, DateTimeUnit.DAY).atTime(defaultReminderTime).toInstant(timeZone)
        .toEpochMilliseconds()
    }

    ChipSelection.NextWeek -> {
      val today = nowInstant.toLocalDateTime(timeZone).date
      today.plus(1, DateTimeUnit.WEEK).atTime(defaultReminderTime).toInstant(timeZone)
        .toEpochMilliseconds()
    }

    is ChipSelection.Custom -> this.timestamp
  }
}