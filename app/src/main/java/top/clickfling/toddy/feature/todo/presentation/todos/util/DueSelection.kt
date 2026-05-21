package top.clickfling.toddy.feature.todo.presentation.todos.util

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.Instant

sealed interface DueSelection {
  data object Today : DueSelection
  data object Tomorrow : DueSelection
  data object NextWeek : DueSelection
  data class Custom(val timestamp: Long?) : DueSelection
  data object Clear : DueSelection
}

fun DueSelection.toDueDays(
  clock: Clock = Clock.System,
  timeZone: TimeZone = TimeZone.currentSystemDefault(),
): Long? {
  val today = clock.todayIn(timeZone)
  return when (this) {
    DueSelection.Clear -> null
    DueSelection.Today -> today.toEpochDays()
    DueSelection.Tomorrow -> today.plus(1, DateTimeUnit.DAY).toEpochDays()
    DueSelection.NextWeek -> today.plus(1, DateTimeUnit.WEEK).toEpochDays()
    is DueSelection.Custom -> {
      if (timestamp == null) return null
      Instant.fromEpochMilliseconds(timestamp)
        .toLocalDateTime(timeZone)
        .date
        .toEpochDays()
    }
  }
}